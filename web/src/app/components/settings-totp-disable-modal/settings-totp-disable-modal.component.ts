import { Component, inject, ViewChild } from '@angular/core';
import { ModalComponent } from '../modal/modal.component';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { settingsService } from '../../services/settings.service';
import { ToastrService } from 'ngx-toastr';
import { NgOtpInputModule } from 'ng-otp-input';

@Component({
  selector: 'app-settings-totp-disable-modal',
  standalone: true,
  imports: [FormsModule, ModalComponent, ReactiveFormsModule, NgOtpInputModule],
  templateUrl: './settings-totp-disable-modal.component.html',
})
export class SettingsTotpDisableModalComponent {
  private settings = inject(settingsService);
  private toastr = inject(ToastrService);

  @ViewChild(ModalComponent) modal!: ModalComponent;
  form = new FormGroup({ totp: new FormControl('') });

  async submitForm() {
    const result = await this.settings.setTotpEnabled(this.form.value.totp ?? undefined, false);
    if (result.success === false) {
      if (result.message === 'INVALID_FIELDS' && result.fields) this.form.setErrors(result.fields);
      if (result.message === 'INVALID_TOTP') this.form.setErrors({ totp: 'Invalid otp code' });
      if (result.message === 'UNKNOWN_ERROR') this.form.setErrors({ totp: 'An unknown error occurred' });

      return;
    }

    this.modal.close();
    this.toastr.success('Two-factor authentication disabled');
  }

  openModal() {
    this.modal.open();
  }

  closeModal() {
    this.form.setErrors(null);
  }
}
