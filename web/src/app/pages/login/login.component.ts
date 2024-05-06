import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router, Routes } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  @ViewChild('login') login!: ElementRef<HTMLDivElement>;
  @ViewChild('2fa') fa!: ElementRef;
  @ViewChild('password') password!: ElementRef;
  @ViewChild('username') username!: ElementRef;
  @ViewChild('2facode') facode!: ElementRef;

  showPassword = false;

  constructor(private router: Router) {}

  onContinueClick(): void {
    if (this.username.nativeElement.value === 'admin' && this.password.nativeElement.value === 'supersecuurwachtwoord1234!') {
      this.login.nativeElement.style.display = 'none';
      this.fa.nativeElement.hidden = false;
    } else {
      alert('Invalid credentials');
    }
  }

  onLoginClick(): void {
    if (this.facode.nativeElement.value === '123456') {
      this.router.navigate(['/batches']);
    } else {
      alert('Invalid 2FA code');
    }
  }

  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }
}
