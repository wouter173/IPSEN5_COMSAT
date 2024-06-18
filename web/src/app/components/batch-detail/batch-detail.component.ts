import { CommonModule } from '@angular/common';
import { Component, computed, inject, Input, Signal, effect, OnInit, OnDestroy } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { RoleService } from '../../services/role.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-batch-detail',
  standalone: true,
  templateUrl: './batch-detail.component.html',
  imports: [LucideAngularModule, SpinnerComponent, FormsModule, CommonModule],
})
export class BatchDetailComponent implements OnDestroy {
  @Input() selectedBatchId!: Signal<string | null>;

  public roleService = inject(RoleService);

  private batchesService = inject(BatchesService);
  private contactService = inject(ContactsService);
  private templateService = inject(TemplatesService);
  public statusService = inject(StatusService);
  private api = inject(ApiService);
  private toastr = inject(ToastrService);

  public editingContact: Contact | null = null;
  public batchEditmode = false;
  public platforms = platforms;
  public poller: number | null = null;

  public templates = this.templateService.templates;
  public selectedBatch = computed(() => this.batchesService.batches().find((batch) => batch.id === this.selectedBatchId()));
  public usedPlatforms = computed(() => {
    const contacts = this.selectedBatch()?.contacts;
    return contacts?.reduce<string[]>((acc, cur) => (acc.includes(cur.platform) ? acc : [...acc, cur.platform]), []) ?? [];
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

  findTemplate(platform: string) {
    return this.selectedBatch()?.templates.find((template) => template.platform === platform);
  }

  checkTemplateId(platform: string, templateId: string) {
    const res = this.selectedBatch()?.templates.find((template) => template.platform === platform)?.id ?? 'NO_TEMPLATE';
    return res === templateId;
  }

  toggleBatchEditMode() {
    this.batchEditmode = !this.batchEditmode;
    if (!this.batchEditmode) {
      this.onSaveBatchChanges();
    }
  }

  onSelectTemplate(e: Event, platform: string) {
    const selectedBatch = this.selectedBatch();
    const inputValue = (e.target as HTMLInputElement).value;

    if (selectedBatch && inputValue !== 'NO_TEMPLATE') {
      const newTemplates = [...selectedBatch.templates.filter((template) => template.platform !== platform).map((t) => t.id), inputValue];

      this.batchesService.updateBatch(selectedBatch.id, { templates: newTemplates }).subscribe({
        next: () => (this.batchEditmode = false),
        error: (error) => console.error('Error updating batch name', error),
      });
    }
  }

  onSaveBatchChanges() {
    const selectedBatch = this.selectedBatch();
    if (selectedBatch) {
      this.batchesService.updateBatch(selectedBatch.id, { name: this.batchName }).subscribe({
        next: () => (this.batchEditmode = false),
        error: (error) => console.error('Error updating batch name', error),
      });
    }
  }

  startPoller() {
    if (this.poller) clearInterval(this.poller);
    this.poller = setInterval(() => this.batchesService.getAllBatches().subscribe(), 1000) as unknown as number;
  }

  async onSendClick() {
    const id = this.selectedBatchId();
    if (!id) return;

    const { response } = await this.api.post(`/batches/${this.selectedBatchId()}/send`);
    if (response.status === 200) {
      this.startPoller();
    } else {
      this.toastr.error(await response.text());
    }
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
