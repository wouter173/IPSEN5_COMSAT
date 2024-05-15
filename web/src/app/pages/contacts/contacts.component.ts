import { Component } from '@angular/core';
import { ContactsListItemComponent } from '../../components/contacts-list-item/contacts-list-item.component';
import { Contact } from '../../models/contact';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contacts',
  standalone: true,
  imports: [ContactsListItemComponent, CommonModule],
  templateUrl: './contacts.component.html',
})
export class ContactsComponent {
  contacts: Contact[] = [
    { id: '1', platform: 'kik', nickname: 'John Doe' },
    { id: '2', platform: 'whatsapp', nickname: 'Jane Smith' },
    { id: '3', platform: 'telegram', nickname: 'Alice Johnson' },
    { id: '4', platform: 'instagram', nickname: 'Bob Williams' },
    { id: '5', platform: 'instagram', nickname: 'Sarah Davis' },
    { id: '6', platform: 'instagram', nickname: 'Michael Brown' },
    { id: '7', platform: 'instagram', nickname: 'Jessica Wilson' },
    { id: '8', platform: 'instagram', nickname: 'David Taylor' },
    { id: '9', platform: 'snapchat', nickname: 'Emily Miller' },
    { id: '10', platform: 'snapchat', nickname: 'Daniel Anderson' },
    { id: '11', platform: 'snapchat', nickname: 'Olivia Martinez' },
    { id: '12', platform: 'telegram', nickname: 'Matthew Thomas' },
    { id: '13', platform: 'whatsapp', nickname: 'Ava Hernandez' },
    { id: '14', platform: 'kik', nickname: 'William Clark' },
    { id: '15', platform: 'whatsapp', nickname: 'Sophia Lewis' },
    { id: '16', platform: 'whatsapp', nickname: 'James Young' },
    { id: '17', platform: 'whatsapp', nickname: 'Charlotte Rodriguez' },
    { id: '18', platform: 'telegram', nickname: 'Benjamin Walker' },
    { id: '19', platform: 'telegram', nickname: 'Mia Hall' },
    { id: '20', platform: 'snapchat', nickname: 'Henry Allen' },
    { id: '21', platform: 'telegram', nickname: 'Ella Green' },
    { id: '22', platform: 'telegram', nickname: 'Alexander Adams' },
    { id: '23', platform: 'telegram', nickname: 'Sofia Hill' },
    { id: '24', platform: 'whatsapp', nickname: 'Joseph Turner' },
    { id: '25', platform: 'kik', nickname: 'Victoria Scott' },
    { id: '26', platform: 'kik', nickname: 'Samuel Phillips' },
    { id: '27', platform: 'kik', nickname: 'Lily Bennett' },
    { id: '28', platform: 'kik', nickname: 'Christopher Reed' },
    { id: '29', platform: 'kik', nickname: 'Grace Cook' },
    { id: '30', platform: 'kik', nickname: 'Andrew Brooks' },
  ];
}
