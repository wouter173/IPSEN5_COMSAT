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
    const { response: result } = await this.api.post('/settings/totp-qr');
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
    const { response } = await this.api.put('/settings/totp', { body: { totp, mfaEnabled: enabled } });

    if (response.status === 400) {
      return { success: false, message: 'INVALID_FIELDS', fields: z.object({ totp: z.string().optional() }).parse(await response.json()) };
    }

    if (response.status === 401) {
      return { success: false, message: 'INVALID_TOTP' };
    }

    if (!response.ok) {
      return { success: false, message: 'UNKNOWN_ERROR' };
    }

    this.user.updateMe();
    return { success: true };
  }
}
