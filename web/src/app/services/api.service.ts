import { inject, Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { LogoutService } from './logout.service';

type Options = Partial<{
  authorized: boolean;
  body: Object;
  version: 'v1';
}>;

@Injectable({ providedIn: 'root' })
export class ApiService {
  private auth = inject(AuthService);
  private logoutService = inject(LogoutService);
  private toastr = inject(ToastrService);

  public async request(method: 'GET' | 'POST' | 'PUT' | 'DELETE', endpoint: string, options?: Options): Promise<Response> {
    options = { authorized: true, version: 'v1', ...options };

    const baseUrl = '/api/' + options.version;
    const url = new URL(baseUrl + endpoint, environment.apiUrl).toString();
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

  public async get(endpoint: string, options?: Omit<Options, 'body'>) {
    return this.request('GET', endpoint, options);
  }

  public async post(endpoint: string, options?: Options) {
    return this.request('POST', endpoint, options);
  }

  public async put(endpoint: string, options?: Options) {
    return this.request('PUT', endpoint, options);
  }

  public async delete(endpoint: string, options?: Omit<Options, 'body'>) {
    return this.request('DELETE', endpoint, options);
  }
}
