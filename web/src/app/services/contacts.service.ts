import { Injectable, inject, signal } from '@angular/core';
import { Observable, from, map, tap } from 'rxjs';
import { z } from 'zod';
import { Contact } from '../models/contact';
import { ContactWithBatch, contactWithBatchSchema } from '../models/contactwithbatch';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root',
})
export class ContactsService {
  private api = inject(ApiService);
  private _contacts = signal<ContactWithBatch[]>([]);
  public contacts = this._contacts.asReadonly();

  constructor() {
    this.getContacts().subscribe();
  }

  public getContacts(): Observable<ContactWithBatch[]> {
    return from(this.api.get('/contacts', { schema: z.array(contactWithBatchSchema) })).pipe(
      map((contacts) => contacts.data),
      tap(this._contacts.set),
    );
  }

  public deleteContact(id: string): Observable<unknown> {
    return from(this.api.delete(`/contacts/${id}`));
  }

  public updateContact(id: string, contact: Contact): Observable<unknown> {
    return from(this.api.put(`/contacts/${id}`, { body: contact }));
  }
}
