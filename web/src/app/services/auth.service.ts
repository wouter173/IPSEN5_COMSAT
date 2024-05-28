import { computed, Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _token = signal(localStorage.getItem('token'));

  public token = this._token.asReadonly();
  public isLoggedIn = computed(() => this.token() !== null);

  private removeToken(): void {
    localStorage.removeItem('token');
    this._token.set(null);
  }

  setToken(token: string): void {
    localStorage.setItem('token', token);
    this._token.set(token);
  }

  logout(): void {
    this.removeToken();
  }

  /**
   * @deprecated use token() instead
   */
  getToken(): string | null {
    return this._token();
  }
}
