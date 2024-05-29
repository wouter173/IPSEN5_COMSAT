import { CommonModule } from '@angular/common';
import { Component, computed, ElementRef, inject, ViewChild } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { NgOtpInputModule } from 'ng-otp-input';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { settingsService } from '../../services/settings.service';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../services/user.service';
import { ModalComponent } from '../../components/modal/modal.component';
import { SettingsTotpEnableModalComponent } from '../../components/settings-totp-enable-modal/settings-totp-enable-modal.component';
import { SettingsTotpDisableModalComponent } from '../../components/settings-totp-disable-modal/settings-totp-disable-modal.component';

@Component({
  selector: 'app-settings',
  standalone: true,
  templateUrl: './settings.component.html',
  imports: [
    LucideAngularModule,
    CommonModule,
    NgOtpInputModule,
    FormsModule,
    ReactiveFormsModule,
    ModalComponent,
    SettingsTotpEnableModalComponent,
    SettingsTotpDisableModalComponent,
  ],
})
export class SettingsComponent {
  private settings = inject(settingsService);
  private toastr = inject(ToastrService);
  private user = inject(UserService);

  mfaEnabled = computed(() => this.user.me()?.mfaEnabled ?? true);
}
