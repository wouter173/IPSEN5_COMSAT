import { CommonModule } from '@angular/common';
import { Component, computed, inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LucideAngularModule } from 'lucide-angular';
import { NgOtpInputModule } from 'ng-otp-input';
import { ModalComponent } from '../../components/modal/modal.component';
import { SettingsTotpDisableModalComponent } from '../../components/settings-totp-disable-modal/settings-totp-disable-modal.component';
import { SettingsTotpEnableModalComponent } from '../../components/settings-totp-enable-modal/settings-totp-enable-modal.component';
import { UserService } from '../../services/user.service';

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
  private user = inject(UserService);

  mfaEnabled = computed(() => this.user.me()?.mfaEnabled ?? true);
}
