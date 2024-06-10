import { Component, inject } from '@angular/core';
import { ContactsListItemComponent } from '../../components/contacts-list-item/contacts-list-item.component';
import { CommonModule } from '@angular/common';
import { ContactsService } from '../../services/contacts.service';
import { ContactWithBatch } from '../../models/contactwithbatch';
import { Contact } from '../../models/contact';

@Component({
  selector: 'app-contacts',
  standalone: true,
  imports: [ContactsListItemComponent, CommonModule],
  templateUrl: './contacts.component.html',
})
export class ContactsComponent {
  contacts: ContactWithBatch[] = [];
  contactService = inject(ContactsService);
  // public selectedContact!: ContactWithBatch;

  ngOnInit() {
    // this.contactService.getContacts().subscribe((data) => {
    //   console.log(data);
    //   this.contacts = data.filter((item) => item !== null);
    //   this.selectedContact = this.contacts[0];
    // });
  }

  // selectContact(contact: ContactWithBatch) {
  //   this.selectedContact = contact;
  // }
}
