import { CommonModule } from '@angular/common';
import { Component, computed, inject, Input, Signal, effect } from '@angular/core';
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

@Component({
  selector: 'app-batch-detail',
  standalone: true,
  templateUrl: './batch-detail.component.html',
  imports: [LucideAngularModule, SpinnerComponent, FormsModule, CommonModule],
})
export class BatchDetailComponent {
  @Input() selectedBatchId!: Signal<string | null>;

  public batchesService = inject(BatchesService);
  public contactService = inject(ContactsService);
  public templateService = inject(TemplatesService);

  public editingContact: Contact | null = null;
  public batchEditmode = false;
  public platforms = platforms;

  public templates = this.templateService.templates;
  public selectedBatch = computed(() => this.batchesService.batches().find((batch) => batch.id === this.selectedBatchId()));
  public usedPlatforms = computed(() => {
    return this.selectedBatch()?.contacts.reduce<string[]>((acc, cur) => (acc.includes(cur.platform) ? acc : [...acc, cur.platform]), []);
  });

  public platformTemplateMap = computed(() => {
    const map = new Map<string, Template[]>();
    this.usedPlatforms()?.forEach((platform) => {
      const templates = this.templates()?.filter((template) => template.platform === platform);
      map.set(platform, templates);
    });
    return map;
  });

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

  async onSendClick() {
    const id = this.selectedBatchId();
    if (!id) return;
    this.batchesService.updateBatch(id, { state: 'SENDING' });
    await sleep(1000);

    this.batchesService.updateBatch(id, {
      contacts: this.selectedBatch()?.contacts.map((contact) => ({ ...contact, status: 'SENDING' })) ?? [],
    });

    await sleep(1000);

    const contactStatus = ['SENT', 'ERROR', 'READ', 'REPLIED'];
    this.batchesService.updateBatch(id, {
      contacts:
        this.selectedBatch()?.contacts.map((contact) => ({
          ...contact,
          status: contactStatus[Math.round(Math.random() * contactStatus.length)] as 'NOTSENT',
        })) ?? [],
    });
    await sleep(2000);
    this.batchesService.updateBatch(id, { state: 'SENT' });

    const batch = this.selectedBatch();
    if (!batch) {
      console.error('Batch not found');
      return;
    }

    this.batchesService.sendBatchData(batch).subscribe(
      (response) => console.log('Batch sent successfully', response),
      (error) => console.error('Error sending batch', error),
    );
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
