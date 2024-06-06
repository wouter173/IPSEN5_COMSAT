import { Injectable, inject } from '@angular/core';
import { Observable, from, map } from 'rxjs';
import { z } from 'zod';
import { Contact } from '../models/contact';
import { ContactWithBatch, contactWithBatchSchema } from '../models/contactwithbatch';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root',
})
export class ContactsService {
  private api = inject(ApiService);

  getContacts(): Observable<ContactWithBatch[]> {
    return from(this.api.get('/contacts', { schema: z.array(contactWithBatchSchema) })).pipe(map((contacts) => contacts.data));
  }

  deleteContact(id: string): Observable<unknown> {
    return from(this.api.delete(`contacts/${id}`));
  }

  updateContact(id: string, contact: Contact): Observable<unknown> {
    return from(this.api.put(`contacts/${id}`, { body: contact }));
  }
}
