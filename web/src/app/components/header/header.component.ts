import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router, Event as Event_2} from "@angular/router";
import {filter} from "rxjs/operators";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  currentPage: string = '';

  constructor(private router: Router) {
    this.router.events.pipe(
      filter((event: Event_2): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe((event: Event_2) => {
      if (event instanceof NavigationEnd) {
        let page = event.urlAfterRedirects;
        page = page.replace('/', '');
        this.currentPage = page.charAt(0).toUpperCase() + page.slice(1);
      }
    });
  }

  ngOnInit(): void {
  }

}
