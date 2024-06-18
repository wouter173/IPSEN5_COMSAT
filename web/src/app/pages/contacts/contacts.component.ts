import { Component, computed, inject, signal } from '@angular/core';
import { ContactsListItemComponent } from '../../components/contacts-list-item/contacts-list-item.component';
import { CommonModule } from '@angular/common';
import { ContactsService } from '../../services/contacts.service';
import { ActivatedRoute } from '@angular/router';
import { LucideAngularModule } from 'lucide-angular';
import { SpinnerComponent } from '../../components/spinner/spinner.component';
import { StatusService } from '../../services/status.service';
import { Contact } from "../../models/contact";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BatchesService } from "../../services/batches.service";
import { platforms } from "../../models/platform";

@Component({
  selector: 'app-contacts',
  standalone: true,
  templateUrl: './contacts.component.html',
  imports: [ContactsListItemComponent, CommonModule, LucideAngularModule, SpinnerComponent, ReactiveFormsModule, FormsModule],
})
export class ContactsComponent {
  private contactService = inject(ContactsService);
  private batchesService = inject(BatchesService);
  private activatedRoute = inject(ActivatedRoute);
  public statusService = inject(StatusService);

  public editingContact: Contact | null = null;
  public platforms = platforms;


  selectedBatchId = signal<string | null>(null);

  public contacts = this.contactService.contacts;
  public contactId = signal<string | null>(null);
  public selectedContact = computed(() => this.contacts().find((contact) => contact.id === this.contactId()));
  public selectedBatch = computed(() => this.batchesService.batches().find((batch) => batch.id === this.selectedBatchId()));

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe((data) => {
      this.contactId.set(data.get('id'));
    });
    this.contactService.getContacts().subscribe();
  }


  onDeleteClick(contactId: string) {
    if (window.confirm('Are you sure you want to delete this contact?')) {
      this.contactService.deleteContact(contactId).subscribe(() => {
        const index = this.contacts().findIndex(contact => contact.id === contactId);
        if (index !== -1) {
          this.contacts().splice(index, 1);
        }
      });
    }
  }

    onEditClick(contact: Contact) {
      //To edit the contact
      this.editingContact = contact;
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
