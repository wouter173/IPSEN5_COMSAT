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

  getPlatformData() {
    const url = environment.apiUrl + '/api/v1/contacts/platorm-data';
    return this.http.get(url, {headers: {
      "Authorization": "Bearer " + this.authService.getToken()
      }});
  }

  getRegionData(): Observable<any> {
    const url = environment.apiUrl + '/api/v1/contacts/region-data';
    return this.http.get(url, {headers: {
      "Authorization": "Bearer " + this.authService.getToken()
      }});
  }

  getGenderData(): Observable<any> {
    const url = environment.apiUrl + '/api/v1/contacts/gender-data';
    return this.http.get(url, {headers: {
      "Authorization": "Bearer " + this.authService.getToken()
      }});
  }
}
