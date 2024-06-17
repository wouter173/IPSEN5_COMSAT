import { Injectable, inject } from '@angular/core';
import { ApiService } from './api.service';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  userService = inject(UserService);

  isAdmin() {
    if (!this.userService.me()) {
      return false;
    }
    return this.userService.me()!.role === 'admin';
  }

  isFict() {
    if (!this.userService.me()) {
      return false;
    }
    return this.userService.me()!.role === 'fict';
  }

  isResearcher() {
    if (!this.userService.me()) {
      return false;
    }
    return this.userService.me()!.role === 'researcher';
  }
  
}
