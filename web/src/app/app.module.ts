import { importProvidersFrom, NgModule } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {
  UserRoundIcon,
  LucideAngularModule,
  ShoppingCartIcon,
  HexagonIcon,
  SearchIcon,
  PlusIcon,
  LogInIcon,
  LogOutIcon,
  UserCog2Icon,
  UserCogIcon,
  CheckIcon,
  TrashIcon,
  ChevronLeftIcon,
  ChevronLeft,
  SettingsIcon,
  Contact,
  ContactIcon,
  SquareUserRoundIcon,
  ContactRoundIcon,
  PieChartIcon,
  UsersIcon,
  LayersIcon,
  ChevronRightIcon,
  FilterIcon,
  XIcon,
  FileIcon,
  FileTextIcon,
  FileX2,
  FileX2Icon,
  ClipboardIcon,
  SendIcon,
  EditIcon, LayoutDashboard,
} from 'lucide-angular';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
// import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule, NoopAnimationsModule, provideAnimations } from '@angular/platform-browser/animations';
import { MatNativeDateModule } from '@angular/material/core';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    // ToastrModule.forRoot(),
    LucideAngularModule.pick({
      EditIcon,
      SendIcon,
      UserRoundIcon,
      FileX2Icon,
      FileTextIcon,
      CheckIcon,
      ChevronLeftIcon,
      ChevronRightIcon,
      ContactRoundIcon,
      FilterIcon,
      HexagonIcon,
      LayersIcon,
      LogInIcon,
      LogOutIcon,
      PieChartIcon,
      PlusIcon,
      SearchIcon,
      SettingsIcon,
      ShoppingCartIcon,
      TrashIcon,
      UserCog2Icon,
      UserCogIcon,
      UsersIcon,
      XIcon,
      ClipboardIcon,
      LayoutDashboard
    }),
  ],
  providers: [provideAnimations(), provideHttpClient(), importProvidersFrom(MatNativeDateModule)],
  bootstrap: [AppComponent],
})
export class AppModule {}
