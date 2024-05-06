import { Component } from '@angular/core';
import { NavlinkComponent } from '../navlink/navlink.component';

import { CommonModule } from '@angular/common';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-sidenav',
  standalone: true,
  templateUrl: './sidenav.component.html',
  imports: [NavlinkComponent, CommonModule, LucideAngularModule],
})
export class SidenavComponent {
  isProfileDropdownOpen: boolean = false;

  toggleProfileDropdown() {
    this.isProfileDropdownOpen = !this.isProfileDropdownOpen;
  }
}
