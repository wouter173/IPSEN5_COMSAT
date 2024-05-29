import { CommonModule } from '@angular/common';
import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { NgOtpInputModule } from 'ng-otp-input';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { settingsService } from '../../services/settings.service';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [LucideAngularModule, CommonModule, NgOtpInputModule, FormsModule, ReactiveFormsModule],
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
})
export class SettingsComponent {
  private settings = inject(settingsService);
  private toastr = inject(ToastrService);
  private user = inject(UserService);

  @ViewChild('totpModal') totpModal!: ElementRef<HTMLDialogElement>;
  imageUrl: null | string = null;
  form = new FormGroup({ totp: new FormControl('') });
  mfaEnabled = this.user.me()?.mfaEnabled ?? true;

  async submitForm() {
    const result = await this.settings.enableTotp(this.form.value.totp ?? undefined);
    if (result.success === false) {
      if (result.message === 'INVALID_FIELDS' && result.fields) {
        this.form.setErrors(result.fields);
      }
      if (result.message === 'INVALID_TOTP') {
        this.form.setErrors({ totp: 'Invalid otp code' });
      }
      if (result.message === 'UNKNOWN_ERROR') {
        this.form.setErrors({ totp: 'An unknown error occurred' });
      }
      return;
    }

    this.closeDialog();
    this.toastr.success('Two-factor authentication enabled');
  }

  async openDialog() {
    this.totpModal.nativeElement.showModal();
    this.totpModal.nativeElement.addEventListener('click', this.dialogClickHandler.bind(this));

    if (!this.imageUrl && this.mfaEnabled === false) {
      this.imageUrl = await this.settings.generateTotpQrCode();
    }
  }

  closeDialog() {
    this.totpModal.nativeElement.close();
    this.totpModal.nativeElement.removeEventListener('click', this.dialogClickHandler);
    this.form.setErrors(null);
  }

  dialogClickHandler(e: MouseEvent) {
    const target = e.target as HTMLDialogElement;

    const rect = target.getBoundingClientRect();
    const clickedInDialog =
      rect.top <= e.clientY && e.clientY <= rect.top + rect.height && rect.left <= e.clientX && e.clientX <= rect.left + rect.width;

    if (clickedInDialog) return;

    this.closeDialog();
  }
}
