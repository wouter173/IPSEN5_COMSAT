import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root',
})
export class ChartService {
  constructor(private http: HttpClient, private authService: AuthService) { }

  getPlatformData(id?: string): Observable<any> {
    let url = environment.apiUrl + '/api/v1/contacts/platform-data';
    if (id) {
      url += `?batchId=${id}`;
    }
    return this.http.get(url, {headers: {
      "Authorization": "Bearer " + this.authService.getToken()
      }});
  }

  getRegionData(id?: string): Observable<any> {
    let url = environment.apiUrl + '/api/v1/contacts/region-data';
    if (id) {
      url += `?batchId=${id}`;
    }
    return this.http.get(url, {headers: {
      "Authorization": "Bearer " + this.authService.getToken()
      }});
  }

  getGenderData(id?: string): Observable<any> {
    let url = environment.apiUrl + '/api/v1/contacts/gender-data';
    if (id) {
      url += '?batchId=' + id;
    }
    return this.http.get(url, {headers: {
      "Authorization": "Bearer " + this.authService.getToken()
      }});
  }
}
