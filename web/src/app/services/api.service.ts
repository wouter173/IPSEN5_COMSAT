import { inject, Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { LogoutService } from './logout.service';
import { z, ZodError, ZodSchema } from 'zod';

type Options<T extends ZodSchema> = Partial<{
  schema: T;
  authorized: boolean;
  body: Object;
  version: 'v1';
}>;

type ApiResponse<T extends ZodSchema | undefined = undefined> = T extends ZodSchema
  ? { response: Response; data: z.infer<T> }
  : { response: Response };

@Injectable({ providedIn: 'root' })
export class ApiService {
  private auth = inject(AuthService);
  private logoutService = inject(LogoutService);
  private toastr = inject(ToastrService);

  public async request<T extends ZodSchema>(
    method: 'GET' | 'POST' | 'PUT' | 'DELETE',
    endpoint: string,
    options?: Options<T>,
  ): Promise<ApiResponse<T>> {
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
        console.log(await response.text());
        this.logoutService.logout();
      }

      if (options.schema) {
        const json = await response.json();
        const data = options.schema.parse(json);

        return { response, data } as ApiResponse<T>;
      }

      return { response } as ApiResponse<T>;
    } catch (error) {
      this.toastr.error('An error occurred');
      console.error(error, { method, endpoint, options });
      if (error instanceof ZodError) {
        console.error(error.message);
      }
      throw error;
    }
  }

  public async get<T extends ZodSchema>(endpoint: string, options?: Omit<Options<T>, 'body'>) {
    return this.request('GET', endpoint, options);
  }

  public async post<T extends ZodSchema>(endpoint: string, options?: Options<T>) {
    return this.request('POST', endpoint, options);
  }

  public async put<T extends ZodSchema>(endpoint: string, options?: Options<T>) {
    return this.request('PUT', endpoint, options);
  }

  public async delete<T extends ZodSchema>(endpoint: string, options?: Omit<Options<T>, 'body'>) {
    return this.request('DELETE', endpoint, options);
  }
}
