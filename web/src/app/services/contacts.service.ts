import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Contact, contactSchema } from '../models/contact';
import { Batch } from '../models/batch';
import { Observable, map } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ContactsService {
  auth = inject(AuthService);
  url = environment.apiUrl;
  token = 'Bearer ' + this.auth.getToken();

  constructor(private http: HttpClient) {}

  contacts: { contact: Contact; batch: Batch }[] = [];

  getContacts(): Observable<{ contact: Contact; batch: Batch }[]> {
    const headers = {
      Authorization: this.token,
    };
    const contactUrl = this.url + '/api/v1/contacts';

    return this.http
      .get<
        {
          id: string;
          firstname: string;
          nickname: string;
          platform: string;
          audience: string;
          sex: string;
          language: string;
          region: string;
          batch: Batch;
        }[]
      >(contactUrl, { headers })
      .pipe(
        map(
          (data) =>
            data
              .map((item) => {
                const contact: Contact = {
                  id: item.id,
                  platform: item.platform as 'snapchat' | 'kik' | 'whatsapp' | 'instagram' | 'telegram',
                  firstName: item.firstname,
                  nickname: item.nickname,
                  audience: item.audience,
                  sex: item.sex,
                  language: item.language,
                  region: item.region,
                };
                try {
                  contactSchema.parse(contact);
                } catch (error) {
                  console.error('Validation error:', error);
                  return null;
                }

                return { contact, batch: item.batch };
              })
              .filter((item) => item !== null) as { contact: Contact; batch: Batch }[],
        ),
      );
  }
}
