import { Component, inject } from '@angular/core';
import { ContactsListItemComponent } from '../../components/contacts-list-item/contacts-list-item.component';
import { Contact, contactSchema } from '../../models/contact';
import { CommonModule } from '@angular/common';
import { Batch } from '../../models/batch';
import { ContactsService } from '../../services/contacts.service';

@Component({
  selector: 'app-contacts',
  standalone: true,
  imports: [ContactsListItemComponent, CommonModule],
  templateUrl: './contacts.component.html',
})
export class ContactsComponent {
  contacts: { contact: Contact; batch: Batch }[] = [];
  contactService = inject(ContactsService);

  ngOnInit() {
    this.contactService.getContacts().subscribe((data) => {
      console.log(data);
      this.contacts = data.filter((item) => item !== null) as { contact: Contact; batch: Batch }[];
    });
  }
}
