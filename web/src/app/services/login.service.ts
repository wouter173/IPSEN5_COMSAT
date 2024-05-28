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
  ): Promise<
    | { success: true }
    | { success: false; message: 'OTP_REQUIRED' | 'BAD_CREDENTIALS' | 'INVALID_FIELDS'; fields?: { username?: string; password?: string } }
  > {
    const result = await this.api.post('/api/v1/auth/login', { username, password }, { authorized: false });

    if (result.status === 400) {
      const fields = z
        .object({ username: z.string().optional(), password: z.string().optional() })
        .optional()
        .parse(await result.json());

      return { success: false, message: 'INVALID_FIELDS', fields };
    }

    if (result.status === 401) {
      return { success: false, message: 'BAD_CREDENTIALS' };
    }

    const data = z.object({ token: z.string() }).parse(await result.json());
    this.auth.setToken(data.token);

    return { success: true };
  }
}
