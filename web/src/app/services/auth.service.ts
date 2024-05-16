import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { z } from 'zod';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  private getToken(): string | null {
    return localStorage.getItem('token');
  }

  private removeToken(): void {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }

  async login(
    username: string,
    password: string,
  ): Promise<
    | { success: true }
    | { success: false; message: 'OTP_REQUIRED' | 'BAD_CREDENTIALS' | 'INVALID_FIELDS'; fields?: { username?: string; password?: string } }
  > {
    const result = await fetch(`${environment.apiUrl}/api/v1/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password }),
    });

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
    this.setToken(data.token);

    return { success: true };
  }

  logout(): void {
    this.removeToken();
  }
}
