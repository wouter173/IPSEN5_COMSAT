import { inject, Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { AuthService } from './auth.service';
import { z } from 'zod';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private api = inject(ApiService);
  private auth = inject(AuthService);

  async login(
    username: string,
    password: string,
    totp?: string,
  ): Promise<
    | { success: true }
    | {
        success: false;
        message: 'TOTP_INVALID' | 'TOTP_REQUIRED' | 'BAD_CREDENTIALS' | 'INVALID_FIELDS' | 'UNKNOWN_ERROR';
        fields?: { username?: string; password?: string };
      }
  > {
    const { response: result } = await this.api.post('/auth/login', { body: { username, password, totp }, authorized: false });

    if (result.status === 400) {
      const fields = z
        .object({ username: z.string().optional(), password: z.string().optional(), totp: z.string().optional() })
        .optional()
        .parse(await result.json());

      if (fields?.totp) return { success: false, message: 'TOTP_REQUIRED', fields };
      return { success: false, message: 'INVALID_FIELDS', fields };
    }

    if (result.status === 401) {
      const json = z.object({ error: z.string() }).safeParse(await result.json());
      if (!json.success) return { success: false, message: 'UNKNOWN_ERROR' };

      const { data } = json;
      if (data.error === 'BAD_CREDENTIALS') return { success: false, message: 'BAD_CREDENTIALS' };
      if (data.error === 'TOTP_INVALID') return { success: false, message: 'TOTP_INVALID' };

      return { success: false, message: 'UNKNOWN_ERROR' };
    }

    const data = z.object({ token: z.string() }).parse(await result.json());
    this.auth.setToken(data.token);

    return { success: true };
  }
}
