import { CommonModule } from '@angular/common';
import { Component, computed, inject, Input, Signal, effect, OnInit, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LucideAngularModule } from 'lucide-angular';
import { Contact } from '../../models/contact';
import { platforms } from '../../models/platform';
import { BatchesService } from '../../services/batches.service';
import { ContactsService } from '../../services/contacts.service';
import { sleep } from '../../utils/mindelay';
import { SpinnerComponent } from '../spinner/spinner.component';
import { TemplatesService } from '../../services/templates.service';
import { Template } from '../../models/templates';
import { ApiService } from '../../services/api.service';
import { StatusService } from '../../services/status.service';

@Component({
  selector: 'app-batch-detail',
  standalone: true,
  templateUrl: './batch-detail.component.html',
  imports: [LucideAngularModule, SpinnerComponent, FormsModule, CommonModule],
})
export class BatchDetailComponent implements OnDestroy {
  @Input() selectedBatchId!: Signal<string | null>;

  public batchesService = inject(BatchesService);
  public contactService = inject(ContactsService);
  public templateService = inject(TemplatesService);
  public statusService = inject(StatusService);
  public api = inject(ApiService);

  public editingContact: Contact | null = null;
  public batchEditmode = false;
  public platforms = platforms;
  public poller: number | null = null;

  public templates = this.templateService.templates;
  public selectedBatch = computed(() => this.batchesService.batches().find((batch) => batch.id === this.selectedBatchId()));
  public usedPlatforms = computed(() => {
    return this.selectedBatch()?.contacts.reduce<string[]>((acc, cur) => (acc.includes(cur.platform) ? acc : [...acc, cur.platform]), []);
  });
  public platformTemplateMap = computed(() => {
    const map: Record<string, Template[]> = {};
    this.usedPlatforms()?.forEach((platform) => {
      const templates = this.templates()?.filter((template) => template.platform === platform);
      map[platform] = templates;
    });
    return Object.entries(map);
  });

  constructor() {
    effect(() => {
      if (this.poller) clearInterval(this.poller);
      console.log(this.selectedBatch()?.state);
      if (this.selectedBatch()?.state !== 'NOTSENT') {
        this.startPoller();
      }
    });
  }

  ngOnDestroy() {
    if (this.poller) clearInterval(this.poller);
  }

  get batchName(): string {
    return this.selectedBatch()?.name || '';
  }

  set batchName(value: string) {
    const selectedBatch = this.selectedBatch();
    if (selectedBatch) {
      selectedBatch.name = value;
    }
  }

  toggleBatchEditMode() {
    this.batchEditmode = !this.batchEditmode;
    if (!this.batchEditmode) {
      this.onSaveBatchChanges();
    }
  }

  onSaveBatchChanges() {
    const selectedBatch = this.selectedBatch();
    if (selectedBatch) {
      this.batchesService.updateBatchName(selectedBatch.id, this.batchName).subscribe({
        next: () => (this.batchEditmode = false),
        error: (error) => console.error('Error updating batch name', error),
      });
    }
  }

  startPoller() {
    this.poller = setInterval(() => this.batchesService.getAllBatches().subscribe(), 1000) as unknown as number;
  }

  async onSendClick() {
    const id = this.selectedBatchId();
    if (!id) return;
    this.batchesService.updateBatch(id, { state: 'SENDING' });

    this.api.post(`/batches/${this.selectedBatchId()}/send`);
  }

  onDeleteClick(contact: Contact) {
    if (window.confirm('Are you sure you want to delete this contact?')) {
      this.contactService.deleteContact(contact.id).subscribe(() => {
        // Remove the deleted contact from the local contacts array
        const index = this.selectedBatch()?.contacts.findIndex((c) => c.id === contact.id);
        if (index !== undefined && index !== -1) {
          this.selectedBatch()?.contacts.splice(index, 1);
        }
      });
    }
  }

  onEditClick(contact: Contact) {
    //To edit the contact
    this.editingContact = contact;
  }

  grayOut(contactId: string, hidden: boolean) {
    this.batchesService.hideBatchEntry(this.selectedBatchId()!, contactId, !hidden);
  }

  onSaveClick() {
    if (this.editingContact) {
      this.contactService.updateContact(this.editingContact.id, this.editingContact).subscribe((updateContact) => {
        this.editingContact = null;

        this.batchesService.getAllBatches().subscribe();
      });
    }
  }
}
