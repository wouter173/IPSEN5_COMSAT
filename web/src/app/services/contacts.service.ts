import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Contact, contactSchema } from '../models/contact';
import { Batch, batchSchema } from '../models/batch';
import { Observable, map } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../../environments/environment';
import { ContactWithBatch, contactWithBatchSchema } from '../models/contactwithbatch';
import { z } from 'zod';

@Injectable({
  providedIn: 'root',
})
export class ContactsService {
  auth = inject(AuthService);
  url = environment.apiUrl;
  token = 'Bearer ' + this.auth.getToken();

  constructor(private http: HttpClient) {}

  getContacts(): Observable<ContactWithBatch[]> {
    const headers = {
      Authorization: this.token,
    };
    const contactUrl = this.url + '/api/v1/contacts';

    return this.http.get<unknown>(contactUrl, { headers }).pipe(
      map(
        (data) => {
          const parsedData = z.array(contactWithBatchSchema).parse(data);
          return parsedData
            .map((item) => {
              const contactAndBatch = contactWithBatchSchema.parse(item);
              return contactAndBatch;
            })
            .filter((contactAndBatch) => contactAndBatch !== null);
          }
      ),
    );
  }
}
