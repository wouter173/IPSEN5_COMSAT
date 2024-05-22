import { Component, Input, inject } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { ContactWithBatch } from '../../models/contactwithbatch';
import { Contact } from '../../models/contact';
import { ContactsComponent } from '../../pages/contacts/contacts.component';

@Component({
  selector: 'app-contacts-list-item',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './contacts-list-item.component.html',
  styleUrl: './contacts-list-item.component.scss',
})
export class ContactsListItemComponent {
  contactComponent = inject(ContactsComponent);
  @Input() contact!: ContactWithBatch;
  @Input() public selected!: boolean;

  selectContact(selectcontact: ContactWithBatch) {
      this.contactComponent.selectContact(selectcontact);
  }
}
