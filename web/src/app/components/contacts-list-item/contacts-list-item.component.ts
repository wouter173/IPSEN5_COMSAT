import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Contact } from '../../models/contact';
import { LucideAngularModule } from 'lucide-angular';
import { Batch } from '../../models/batch';
import { ContactWithBatch } from '../../models/contactwithbatch';

@Component({
  selector: 'app-contacts-list-item',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './contacts-list-item.component.html',
  styleUrl: './contacts-list-item.component.scss',
})
export class ContactsListItemComponent {
  @Input() contact!: ContactWithBatch;
  @Input() public selected!: boolean;
}
