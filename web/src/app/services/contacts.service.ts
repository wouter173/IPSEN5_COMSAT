import { Injectable, inject, signal } from '@angular/core';
import { Observable, from, map, take, tap } from 'rxjs';
import { z } from 'zod';
import { Contact } from '../models/contact';
import { ApiService } from './api.service';
import { ContactWithEntries, contactWithEntriesSchema } from '../models/contact-with-entries';

@Injectable({
  providedIn: 'root',
})
export class ContactsService {
  private api = inject(ApiService);
  private _contacts = signal<ContactWithEntries[]>([]);
  public contacts = this._contacts.asReadonly();

  constructor() {
    this.getContacts().subscribe();
  }

  public getContacts(): Observable<Contact[]> {
    return from(this.api.get('/contacts', { schema: z.array(contactWithEntriesSchema) })).pipe(
      map((contacts) => contacts.data),
      tap(this._contacts.set),
      take(1),
    );
  }

  public deleteContact(id: string): Observable<unknown> {
    return from(this.api.delete(`/contacts/${id}`));
  }

  public updateContact(id: string, contact: Contact): Observable<unknown> {
    return from(this.api.put(`/contacts/${id}`, { body: contact }));
  }
}
