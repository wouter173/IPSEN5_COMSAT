import { Component } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { Contact } from '../../models/contact';
import { Batch } from '../../models/batch';
import { ContactWithBatch } from '../../models/contactwithbatch';

@Component({
  selector: 'app-contact-detail',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './contact-detail.component.html',
})
export class ContactDetailComponent {
  contacts!: ContactWithBatch;
}
