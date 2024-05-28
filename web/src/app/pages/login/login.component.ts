import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { Router, Routes } from '@angular/router';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { LucideAngularModule } from 'lucide-angular';
import { ToastrService } from 'ngx-toastr';
import { SpinnerComponent } from '../../components/spinner/spinner.component';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  imports: [ReactiveFormsModule, FormsModule, LucideAngularModule, SpinnerComponent, CommonModule],
})
export class LoginComponent {
  form = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  showPassword = false;

  private loginService = inject(LoginService);
  private router = inject(Router);
  private toastr = inject(ToastrService);

  async onLoginClick(): Promise<void> {
    this.form.disable();
    await new Promise((resolve) => setTimeout(resolve, 1000));

    const result = await this.loginService.login(this.form.value.username ?? '', this.form.value.password ?? '');
    if (result.success === false) {
      console.log(result);
      if (result.message === 'INVALID_FIELDS' && result.fields) {
        this.form.enable();
        this.form.setErrors(result.fields);
      }
      if (result.message === 'BAD_CREDENTIALS') {
        this.toastr.error('Invalid username or password');
        this.form.enable();
      }

      return;
    }

    if (result.success) this.router.navigate(['/']);
  }

  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }
}
