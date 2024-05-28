import { inject, Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { LogoutService } from './logout.service';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private auth = inject(AuthService);
  private logoutService = inject(LogoutService);
  private toastr = inject(ToastrService);

  public async request(
    method: 'GET' | 'POST' | 'PUT' | 'DELETE',
    endpoint: string,
    options?: { authorized?: boolean; body?: Object },
  ): Promise<Response> {
    if (!options) options = {};
    options.authorized = options.authorized === undefined || options.authorized === true;

    const url = new URL(endpoint, environment.apiUrl).toString();
    const init: RequestInit = { method, headers: {} };

    if (options.body) {
      init.body = JSON.stringify(options.body);
      init.headers = { ...init.headers, 'Content-Type': 'application/json' };
    }

    if (options.authorized) {
      const token = this.auth.token();
      init.headers = { ...init.headers, Authorization: `Bearer ${token}` };
    }

    try {
      const response = await fetch(url, init);

      if (response.status > 499) {
        const text = await response.text();
        throw new Error(`[${response.status}] ${response.statusText}: ${text}`);
      }

      if (options.authorized && response.status === 403) {
        this.logoutService.logout();
      }

      return response;
    } catch (error) {
      this.toastr.error('An error occurred');
      console.error(error);
      throw error;
    }
  }

  public async get(endpoint: string, options?: { authorized?: boolean }) {
    return this.request('GET', endpoint, options);
  }

  public async post(endpoint: string, body: Object, options?: { authorized?: boolean }) {
    return this.request('POST', endpoint, { body, ...options });
  }

  public async put(endpoint: string, body: Object, options?: { authorized?: boolean }) {
    return this.request('PUT', endpoint, { body, ...options });
  }

  public async delete(endpoint: string, options?: { authorized?: boolean }) {
    return this.request('DELETE', endpoint, options);
  }
}
