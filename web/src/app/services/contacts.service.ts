import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ContactsService {

  constructor(private http: HttpClient) { }

  getContacts() {
    const headers = { Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE3MTU4NTczNzMsImV4cCI6MTcxNTk0Mzc3M30.v_XP35zPXdoqQYfI5NPkxRwerBagmfV5jm9oYq3Z4oY'};
    this.http.get('http://127.0.0.1:8080/api/v1/contacts', { headers }).subscribe(data => {
      console.log(data);
    }, error => {
      console.error('Error:', error);
    });
  }
  
}