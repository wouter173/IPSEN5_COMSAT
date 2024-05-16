import { Component, inject } from '@angular/core';
import { NavlinkComponent } from '../navlink/navlink.component';

import { CommonModule } from '@angular/common';
import { LucideAngularModule } from 'lucide-angular';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidenav',
  standalone: true,
  templateUrl: './sidenav.component.html',
  imports: [NavlinkComponent, CommonModule, LucideAngularModule],
})
export class SidenavComponent {
  isProfileDropdownOpen: boolean = false;

  auth = inject(AuthService);
  router = inject(Router);

  toggleProfileDropdown() {
    this.isProfileDropdownOpen = !this.isProfileDropdownOpen;
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
