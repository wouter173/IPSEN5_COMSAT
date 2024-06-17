import { Component, HostListener, inject, OnInit } from '@angular/core';
import { NavigationEnd, Router, Event } from '@angular/router';
import { filter } from 'rxjs/operators';
import { RevalidateService } from '../../services/revalidate.service';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  currentPage: string = '';
  hasScrolled: boolean = false;

  constructor(private router: Router) {
    this.router.events.pipe(filter((event: Event): event is NavigationEnd => event instanceof NavigationEnd)).subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        let page = event.urlAfterRedirects.split('/')[1];
        if (page === '') page = 'Dashboard';
        this.currentPage = page.charAt(0).toUpperCase() + page.slice(1);
      }
    });
  }

  @HostListener('window:scroll')
  onWindowScroll() {
    this.hasScrolled = window.scrollY > 8;
  }
}
