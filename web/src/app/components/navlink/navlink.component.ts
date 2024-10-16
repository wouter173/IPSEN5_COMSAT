import { CommonModule } from '@angular/common';
import { Component, inject, Input, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-navlink',
  standalone: true,
  imports: [RouterLink, LucideAngularModule, CommonModule],
  templateUrl: './navlink.component.html',
})
export class NavlinkComponent implements OnInit {
  @Input() pathname: string;
  @Input() icon: string;
  @Input() key: string;

  private router = inject(Router);
  isActive: boolean = false;

  constructor() {
    this.pathname = '';
    this.icon = '';
    this.key = '';
    this.updateCurrentPath();
  }

  ngOnInit(): void {
    this.updateCurrentPath();
  }

  private updateCurrentPath(): void {
    this.isActive = this.isCurrentPath(this.pathname);

    this.router.events.subscribe(() => {
      this.isActive = this.isCurrentPath(this.pathname);
    });
  }

  private isCurrentPath(pathname: string): boolean {
    if (!pathname) return false;
    if (pathname === '/') return this.router.url === pathname;
    return this.router.url.startsWith(pathname);
  }
}
