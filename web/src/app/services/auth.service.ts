import { computed, Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _token = signal(localStorage.getItem('token'));

  token = this._token.asReadonly();
  isLoggedIn = computed(() => this.token() !== null);

  removeToken(): void {
    localStorage.removeItem('token');
    this._token.set(null);
  }

  setToken(token: string): void {
    localStorage.setItem('token', token);
    this._token.set(token);
  }

  /**
   * @deprecated use token() instead
   */
  getToken(): string | null {
    return this._token();
  }
}
