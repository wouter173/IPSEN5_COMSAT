import { inject, Injectable, signal } from '@angular/core';
import { ApiService } from './api.service';
import { z } from 'zod';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private _me = signal<User | null>(null);
  public me = this._me.asReadonly();

  private api = inject(ApiService);

  constructor() {
    this.updateMe();
  }

  public async updateMe() {
    const schema = z.object({
      username: z.string(),
      role: z.string(),
      mfaEnabled: z.boolean(),
    });

    const { data } = await this.api.get('/users/me', { schema });

    this._me.set(data!);
  }
}
