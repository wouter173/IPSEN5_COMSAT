import { importProvidersFrom, NgModule } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import {
  CheckIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
  ClipboardIcon,
  ContactRoundIcon,
  EditIcon,
  EyeIcon,
  EyeOffIcon,
  FileTextIcon,
  FileX2Icon,
  FilterIcon,
  HexagonIcon,
  LayersIcon,
  LayoutDashboard,
  LogInIcon,
  LogOutIcon,
  LucideAngularModule,
  PieChartIcon,
  PlusIcon,
  SearchIcon,
  SendIcon,
  SettingsIcon,
  ShoppingCartIcon,
  TrashIcon,
  UserCog2Icon,
  UserCogIcon,
  UserRoundIcon,
  UsersIcon,
  BoxSelect,
  XIcon,
} from 'lucide-angular';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ToastrModule } from 'ngx-toastr';
import { MatNativeDateModule } from '@angular/material/core';
import { BrowserAnimationsModule, NoopAnimationsModule, provideAnimations } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    ToastrModule.forRoot(),
    LucideAngularModule.pick({
      CheckIcon,
      ChevronLeftIcon,
      ChevronRightIcon,
      ClipboardIcon,
      ContactRoundIcon,
      EditIcon,
      EyeIcon,
      EyeOffIcon,
      FileTextIcon,
      FileX2Icon,
      FilterIcon,
      HexagonIcon,
      LayersIcon,
      LayoutDashboard,
      LogInIcon,
      LogOutIcon,
      PieChartIcon,
      PlusIcon,
      SearchIcon,
      SendIcon,
      SettingsIcon,
      ShoppingCartIcon,
      TrashIcon,
      UserCog2Icon,
      UserCogIcon,
      UserRoundIcon,
      BoxSelect,
      UsersIcon,
      XIcon,
    }),
  ],
  providers: [provideAnimations(), provideHttpClient(), importProvidersFrom(MatNativeDateModule)],
  bootstrap: [AppComponent],
})
export class AppModule {}
