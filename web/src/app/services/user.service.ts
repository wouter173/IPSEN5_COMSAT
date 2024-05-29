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
    const result = await this.api.get('/api/v1/users/me');
    const data = z
      .object({
        username: z.string(),
        role: z.string(),
        mfaEnabled: z.boolean(),
      })
      .parse(await result.json());

    this._me.set(data);
  }
}
