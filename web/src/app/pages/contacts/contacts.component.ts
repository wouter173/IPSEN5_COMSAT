import { Component, inject } from '@angular/core';
import { ContactsListItemComponent } from '../../components/contacts-list-item/contacts-list-item.component';
import { Contact, contactSchema } from '../../models/contact';
import { CommonModule } from '@angular/common';
import { Batch } from '../../models/batch';
import { z } from 'zod';
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
    this.contactService.getContacts();
    for (const batch of this.batches) {
      for (const contact of batch.contacts) {
        this.contacts.push({ contact, batch });
      }
    }
  }

  batches: Batch[] = [
    {
      id: '1',
      state: 'NOTSENT',
      name: 'First Batch',
      contacts: [
        {
          id: '1',
          platform: 'kik',
          nickname: 'John Doe',
          status: 'NOTSENT',
        },
        {
          id: '2',
          platform: 'whatsapp',
          nickname: 'Jane Smith',
          status: 'NOTSENT',
        },
        {
          id: '3',
          platform: 'telegram',
          nickname: 'Alice Johnson',
          status: 'NOTSENT',
        },
        {
          id: '4',
          platform: 'instagram',
          nickname: 'Bob Williams',
          status: 'NOTSENT',
        },
        {
          id: '5',
          platform: 'instagram',
          nickname: 'Sarah Davis',
          status: 'NOTSENT',
        },
        {
          id: '6',
          platform: 'instagram',
          nickname: 'Michael Brown',
          status: 'NOTSENT',
        },
        {
          id: '7',
          platform: 'instagram',
          nickname: 'Jessica Wilson',
          status: 'NOTSENT',
        },
        {
          id: '8',
          platform: 'instagram',
          nickname: 'David Taylor',
          status: 'NOTSENT',
        },
        {
          id: '9',
          platform: 'snapchat',
          nickname: 'Emily Miller',
          status: 'NOTSENT',
        },
        {
          id: '10',
          platform: 'snapchat',
          nickname: 'Daniel Anderson',
          status: 'NOTSENT',
        },
        {
          id: '11',
          platform: 'snapchat',
          nickname: 'Olivia Martinez',
          status: 'NOTSENT',
        },
        {
          id: '12',
          platform: 'telegram',
          nickname: 'Matthew Thomas',
          status: 'NOTSENT',
        },
        {
          id: '13',
          platform: 'whatsapp',
          nickname: 'Ava Hernandez',
          status: 'NOTSENT',
        },
        {
          id: '14',
          platform: 'kik',
          nickname: 'William Clark',
          status: 'NOTSENT',
        },
        {
          id: '15',
          platform: 'whatsapp',
          nickname: 'Sophia Lewis',
          status: 'NOTSENT',
        },
        {
          id: '16',
          platform: 'whatsapp',
          nickname: 'James Young',
          status: 'NOTSENT',
        },
        {
          id: '17',
          platform: 'whatsapp',
          nickname: 'Charlotte Rodriguez',
          status: 'NOTSENT',
        },
        {
          id: '18',
          platform: 'telegram',
          nickname: 'Benjamin Walker',
          status: 'NOTSENT',
        },
        {
          id: '19',
          platform: 'telegram',
          nickname: 'Mia Hall',
          status: 'NOTSENT',
        },
        {
          id: '20',
          platform: 'snapchat',
          nickname: 'Henry Allen',
          status: 'NOTSENT',
        },
        {
          id: '21',
          platform: 'telegram',
          nickname: 'Ella Green',
          status: 'NOTSENT',
        },
        {
          id: '22',
          platform: 'telegram',
          nickname: 'Alexander Adams',
          status: 'NOTSENT',
        },
        {
          id: '23',
          platform: 'telegram',
          nickname: 'Sofia Hill',
          status: 'NOTSENT',
        },
        {
          id: '24',
          platform: 'whatsapp',
          nickname: 'Joseph Turner',
          status: 'NOTSENT',
        },
        {
          id: '25',
          platform: 'kik',
          nickname: 'Victoria Scott',
          status: 'NOTSENT',
        },
        {
          id: '26',
          platform: 'kik',
          nickname: 'Samuel Phillips',
          status: 'NOTSENT',
        },
        {
          id: '27',
          platform: 'kik',
          nickname: 'Lily Bennett',
          status: 'NOTSENT',
        },
        {
          id: '28',
          platform: 'kik',
          nickname: 'Christopher Reed',
          status: 'NOTSENT',
        },
        {
          id: '29',
          platform: 'kik',
          nickname: 'Grace Cook',
          status: 'NOTSENT',
        },
        {
          id: '30',
          platform: 'kik',
          nickname: 'Andrew Brooks',
          status: 'NOTSENT',
        },
      ],
      createdAt: new Date(),
      lastModified: new Date(),
    },
    {
      id: '2',
      state: 'NOTSENT',
      name: 'Second Batch',
      contacts: [
        // Add contacts for the second batch here
        {
          id: '31',
          platform: 'whatsapp',
          nickname: 'Oliver Wilson',
          status: 'NOTSENT',
        },
        {
          id: '32',
          platform: 'telegram',
          nickname: 'Sophie Turner',
          status: 'NOTSENT',
        },
        {
          id: '33',
          platform: 'snapchat',
          nickname: 'Lucas Harris',
          status: 'NOTSENT',
        },
        {
          id: '34',
          platform: 'kik',
          nickname: 'Isabella Martinez',
          status: 'NOTSENT',
        },
        {
          id: '35',
          platform: 'whatsapp',
          nickname: 'Noah Thompson',
          status: 'NOTSENT',
        },
        {
          id: '36',
          platform: 'telegram',
          nickname: 'Emma White',
          status: 'NOTSENT',
        },
        {
          id: '37',
          platform: 'snapchat',
          nickname: 'Liam Turner',
          status: 'NOTSENT',
        },
        {
          id: '38',
          platform: 'kik',
          nickname: 'Ava Davis',
          status: 'NOTSENT',
        },
        {
          id: '39',
          platform: 'whatsapp',
          nickname: 'Mason Wilson',
          status: 'NOTSENT',
        },
        {
          id: '40',
          platform: 'telegram',
          nickname: 'Olivia Harris',
          status: 'NOTSENT',
        },
      ],
      createdAt: new Date(),
      lastModified: new Date(),
    },
    {
      id: '3',
      state: 'NOTSENT',
      name: 'Third Batch',
      contacts: [
        // Add contacts for the third batch here
        {
          id: '41',
          platform: 'snapchat',
          nickname: 'Ethan Anderson',
          status: 'NOTSENT',
        },
        {
          id: '42',
          platform: 'whatsapp',
          nickname: 'Amelia Clark',
          status: 'NOTSENT',
        },
        {
          id: '43',
          platform: 'telegram',
          nickname: 'Liam Johnson',
          status: 'NOTSENT',
        },
        {
          id: '44',
          platform: 'kik',
          nickname: 'Olivia Martinez',
          status: 'NOTSENT',
        },
        {
          id: '45',
          platform: 'whatsapp',
          nickname: 'Noah Thompson',
          status: 'NOTSENT',
        },
        {
          id: '46',
          platform: 'telegram',
          nickname: 'Emma White',
          status: 'NOTSENT',
        },
        {
          id: '47',
          platform: 'snapchat',
          nickname: 'Liam Turner',
          status: 'NOTSENT',
        },
        {
          id: '48',
          platform: 'kik',
          nickname: 'Ava Davis',
          status: 'NOTSENT',
        },
        {
          id: '49',
          platform: 'whatsapp',
          nickname: 'Mason Wilson',
          status: 'NOTSENT',
        },
        {
          id: '50',
          platform: 'telegram',
          nickname: 'Olivia Harris',
          status: 'NOTSENT',
        },
      ],
      createdAt: new Date(),
      lastModified: new Date(),
    },
  ];

}
