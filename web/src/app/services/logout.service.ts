import { inject, Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class LogoutService {
  private auth = inject(AuthService);
  private router = inject(Router);

  async logout() {
    this.auth.removeToken();
    this.router.navigate(['login']);
  }
}
