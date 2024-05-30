import { Component, computed, inject, ViewChild } from '@angular/core';
import { ModalComponent } from '../modal/modal.component';
import { settingsService } from '../../services/settings.service';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../services/user.service';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgOtpInputModule } from 'ng-otp-input';

@Component({
  selector: 'app-settings-totp-enable-modal',
  standalone: true,
  templateUrl: './settings-totp-enable-modal.component.html',
  imports: [ModalComponent, NgOtpInputModule, FormsModule, ReactiveFormsModule],
})
export class SettingsTotpEnableModalComponent {
  private settings = inject(settingsService);
  private toastr = inject(ToastrService);
  private user = inject(UserService);

  @ViewChild(ModalComponent) modal!: ModalComponent;
  imageUrl: null | string = null;
  form = new FormGroup({ totp: new FormControl('') });
  mfaEnabled = computed(() => this.user.me()?.mfaEnabled ?? true);

  async submitForm() {
    const result = await this.settings.setTotpEnabled(this.form.value.totp ?? undefined);
    if (result.success === false) {
      if (result.message === 'INVALID_FIELDS' && result.fields) this.form.setErrors(result.fields);
      if (result.message === 'INVALID_TOTP') this.form.setErrors({ totp: 'Invalid otp code' });
      if (result.message === 'UNKNOWN_ERROR') this.form.setErrors({ totp: 'An unknown error occurred' });

      return;
    }

    this.modal.close();
    this.toastr.success('Two-factor authentication enabled');
  }

  async openModal() {
    this.modal.open();

    if (!this.imageUrl && this.mfaEnabled() === false) {
      this.imageUrl = await this.settings.generateTotpQrCode();
    }
  }

  closeModal() {
    this.form.setErrors(null);
  }
}
