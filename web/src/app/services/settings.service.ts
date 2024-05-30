import { inject, Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { z } from 'zod';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class settingsService {
  private api = inject(ApiService);
  private user = inject(UserService);

  async generateTotpQrCode() {
    const result = await this.api.post('/api/v1/settings/totp-qr');
    const data = z
      .object({
        qrCode: z.string(),
      })
      .parse(await result.json());

    return data.qrCode;
  }

  async setTotpEnabled(
    totp?: string,
    enabled = true,
  ): Promise<
    | { success: false; message: 'INVALID_FIELDS'; fields: { totp?: string } }
    | { success: false; message: 'INVALID_TOTP' }
    | { success: false; message: 'UNKNOWN_ERROR' }
    | { success: true }
  > {
    const result = await this.api.put('/api/v1/settings/totp', { body: { totp, mfaEnabled: enabled } });

    if (result.status === 400) {
      return { success: false, message: 'INVALID_FIELDS', fields: z.object({ totp: z.string().optional() }).parse(await result.json()) };
    }

    if (result.status === 401) {
      return { success: false, message: 'INVALID_TOTP' };
    }

    if (!result.ok) {
      return { success: false, message: 'UNKNOWN_ERROR' };
    }

    this.user.updateMe();
    return { success: true };
  }
}
