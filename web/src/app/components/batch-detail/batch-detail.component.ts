import {Component, computed, effect, inject, Inject, Input, signal, Signal} from '@angular/core';
import {BatchesService} from '../../services/batches.service';
import {ContactsService} from '../../services/contacts.service';
import {Batch} from '../../models/batch';
import {LucideAngularModule} from 'lucide-angular';
import {platforms} from '../../models/platform';
import {SpinnerComponent} from '../spinner/spinner.component';
import {minDelay, sleep} from '../../utils/mindelay';
import {Contact} from "../../models/contact";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-batch-detail',
  standalone: true,
  templateUrl: './batch-detail.component.html',
  styleUrl: './batch-detail.component.scss',
  imports: [LucideAngularModule, SpinnerComponent, FormsModule, CommonModule],
})
export class BatchDetailComponent {
  private _batchName = '';
  @Input() selectedBatchId!: Signal<string | null>;
  public batchesService = inject(BatchesService);
  public selectedBatch = computed(() => this.batchesService.batches().find((batch) => batch.id === this.selectedBatchId()));
  public editingContact: Contact | null = null;
  public batchEditmode = false;
  public platforms = platforms;
  selectedContacts: Contact[] = [];

  constructor(private contactService: ContactsService) {

  }

  get batchName(): string {
    return this.selectedBatch()?.name || '';
  }

  set batchName(value: string) {
    this._batchName = value;
    console.log('New batch name:', value);
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
      this.batchesService.updateBatchName(selectedBatch.id, this.batchName)
        .subscribe(() => {
          this.batchEditmode = false;
        }, error => {
          console.error('Error updating batch name', error);
        });
    }
  }

  async onSendClick() {
    const id = this.selectedBatchId();
    if (!id) return;
    this.batchesService.updateBatch(id, {state: 'SENDING'});
    await sleep(1000);

    this.batchesService.updateBatch(id, {
      contacts: this.selectedBatch()?.contacts.map((contact) => ({...contact, status: 'SENDING'})) ?? [],
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
    this.batchesService.updateBatch(id, {state: 'SENT'});

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

  catch(error: unknown) {
    console.error('Error sending batch', error);
  }

  onDeleteClick(contact: Contact) {
    if (window.confirm('Are you sure you want to delete this contact?')) {
      this.contactService.deleteContact(contact.id).subscribe(() => {
        // Remove the deleted contact from the local contacts array
        const index = this.selectedBatch()?.contacts.findIndex(c => c.id === contact.id);
        if (index !== undefined && index !== -1) {
          this.selectedBatch()?.contacts.splice(index, 1);
        }
      });
    }
  }

  onEditClick(contact: Contact) { //To edit the contact
    this.editingContact = contact;
  }

  grayOut(contact: Contact) {
    const foundContact = this.selectedContacts.find(c => c.id === contact.id);
    if (!foundContact) {
      this.selectedContacts.push(contact);
    } else {
      this.selectedContacts = this.selectedContacts.filter(c => c.id !== contact.id);
    }
  }

  onSaveClick() { //Click the save button in editing mode
    console.log(this.editingContact?.nickname);
    if (this.editingContact) {
      this.contactService.updateContact(this.editingContact.id, this.editingContact).subscribe((updateContact) => {
        this.editingContact = null;
      });
    }
  }
}
