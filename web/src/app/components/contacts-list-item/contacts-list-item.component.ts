import { Component, Input, inject } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { Contact } from '../../models/contact';
import { ContactsComponent } from '../../pages/contacts/contacts.component';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-contacts-list-item',
  standalone: true,
  imports: [LucideAngularModule, RouterLink, RouterLinkActive],
  templateUrl: './contacts-list-item.component.html',
})
export class ContactsListItemComponent {
  contactComponent = inject(ContactsComponent);
  @Input() contact!: Contact;
  @Input() public selected!: boolean;
}
