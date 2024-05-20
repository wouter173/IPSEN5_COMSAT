import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Contact, contactSchema } from '../models/contact';
import { Batch } from '../models/batch';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ContactsService {
  constructor(private http: HttpClient) {}
  contacts: { contact: Contact; batch: Batch }[] = [];

  getContacts(): Observable<{ contact: Contact; batch: Batch }[]> {
    const headers = {
      Authorization:
        'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE3MTYyMDA0NDYsImV4cCI6MTcxNjI4Njg0Nn0.SaAZnz6dLB8X6YzCn11Kk7knTY8trzPNrzL4fWvLr-w',
    };
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
      >('http://127.0.0.1:8080/api/v1/contacts', { headers })
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
