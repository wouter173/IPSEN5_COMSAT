import { inject, Injectable, signal } from '@angular/core';
import { ApiService } from './api.service';
import { z } from 'zod';
import { Engine, engineSchema } from '../models/engine';

@Injectable({
  providedIn: 'root',
})
export class EnginesService {
  private api = inject(ApiService);
  private _engines = signal<Engine[]>([]);

  public engines = this._engines.asReadonly();

  constructor() {
    this.getEngines();
  }

  public async getEngines() {
    const result = await this.api.get('/engines', { schema: z.array(engineSchema) });
    this._engines.set(result.data);
    return result.data;
  }

  public async solveCaptcha(token: string) {
    const result = await this.api.post('/engines/solve-captcha', { body: { token } });
    await this.getEngines();
    return result.response;
  }

  public captchaWithUrlRedirect(captchaUrl: string) {
    const url = new URL(captchaUrl);
    const callbackUrl = window.location.origin + '/engines/captcha-callback';

    url.searchParams.set('callback_url', callbackUrl);

    return url.toString();
  }
}
