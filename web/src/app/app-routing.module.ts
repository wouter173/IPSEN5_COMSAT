import { NgModule } from '@angular/core';
import { RouterModule, Routes, provideRouter, withViewTransitions } from '@angular/router';
import { ContactsComponent } from './pages/contacts/contacts.component';
import { DashboardComponent } from './layouts/dashboard/dashboard.component';
import { UsersComponent } from './pages/users/users.component';
import { ReportsComponent } from './pages/reports/reports.component';
import { BatchesComponent } from './pages/batches/batches.component';
import { LoginComponent } from './pages/login/login.component';
import { TemplatesComponent } from './pages/templates/templates.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    canActivate: [authGuard],
    path: '',
    component: DashboardComponent,
    children: [
      { path: 'batches', component: BatchesComponent },
      { path: 'contacts', component: ContactsComponent },
      { path: 'templates', component: TemplatesComponent },
      { path: 'users', component: UsersComponent },
      { path: '', component: ReportsComponent },
    ],
  },
];

@NgModule({
  providers: [provideRouter(routes, withViewTransitions({ skipInitialTransition: true }))],
  exports: [RouterModule],
})
export class AppRoutingModule {}
