import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Contact, contactSchema } from '../models/contact';
import { Batch, batchSchema } from '../models/batch';
import { Observable, map } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../../environments/environment';
import { contactWithBatchSchema } from '../models/contactwithbatch';
import { z } from 'zod';

@Injectable({
  providedIn: 'root',
})
export class ContactsService {
  auth = inject(AuthService);
  url = environment.apiUrl;
  token = 'Bearer ' + this.auth.getToken();

  constructor(private http: HttpClient) {}

  getContacts(): Observable<{ contact: Contact; batch: Batch }[]> {
    const headers = {
      Authorization: this.token,
    };
    const contactUrl = this.url + '/api/v1/contacts';

    return this.http.get<any[]>(contactUrl, { headers }).pipe(
      map(
        (data) =>
          data
            .map((item) => {
              item.batch.createdAt = new Date(item.batch.createdAt);
              item.batch.lastModified = new Date(item.batch.lastModified);
              const contactAndBatch = contactWithBatchSchema.parse(item);
              return { contact: contactAndBatch, batch: contactAndBatch.batch };
            })
            .filter((contactAndBatch) => contactAndBatch !== null) as { contact: Contact; batch: Batch }[],
      ),
    );
  }
}
