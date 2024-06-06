import { Component, inject } from '@angular/core';
import { NavlinkComponent } from '../navlink/navlink.component';

import { CommonModule } from '@angular/common';
import { LucideAngularModule } from 'lucide-angular';
import { LogoutService } from '../../services/logout.service';

@Component({
  selector: 'app-sidenav',
  standalone: true,
  templateUrl: './sidenav.component.html',
  imports: [NavlinkComponent, CommonModule, LucideAngularModule],
})
export class SidenavComponent {
  isProfileDropdownOpen: boolean = false;

  logoutService = inject(LogoutService);

  toggleProfileDropdown() {
    this.isProfileDropdownOpen = !this.isProfileDropdownOpen;
  }

  logout() {
    this.logoutService.logout();
  }
}
