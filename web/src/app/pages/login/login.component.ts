import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LucideAngularModule } from 'lucide-angular';
import { ToastrService } from 'ngx-toastr';
import { SpinnerComponent } from '../../components/spinner/spinner.component';
import { LoginService } from '../../services/login.service';
import { NgOtpInputModule } from 'ng-otp-input';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  imports: [ReactiveFormsModule, FormsModule, LucideAngularModule, SpinnerComponent, CommonModule, NgOtpInputModule],
})
export class LoginComponent {
  form = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    totp: new FormControl(''),
  });

  showPassword = false;
  showOtpField = false;

  private loginService = inject(LoginService);
  private router = inject(Router);
  private toastr = inject(ToastrService);

  async onLoginClick(): Promise<void> {
    this.form.disable();
    await new Promise((resolve) => setTimeout(resolve, 200));

    const result = await this.loginService.login(
      this.form.value.username ?? '',
      this.form.value.password ?? '',
      this.form.value.totp ?? undefined,
    );
    if (result.success === false) {
      if (result.message === 'TOTP_REQUIRED') {
        this.form.enable();
        if (result.fields !== undefined && this.showOtpField) this.form.setErrors(result.fields);
        this.showOtpField = true;
      }

      if (result.message === 'INVALID_FIELDS' && result.fields) {
        this.form.enable();
        this.form.setErrors(result.fields);
      }

      if (result.message === 'BAD_CREDENTIALS') {
        this.toastr.error('Invalid username or password');
        this.form.enable();
      }

      if (result.message === 'TOTP_INVALID') {
        this.toastr.error('Invalid two-factor code');
        this.form.enable();
      }

      return;
    }

    if (result.success){
      this.router.navigate(['/']);
    }
  }

  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }
}
