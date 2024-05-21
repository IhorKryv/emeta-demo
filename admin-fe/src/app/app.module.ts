import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {CommonModule} from "@angular/common";
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AdminTemplateModule} from "./template/admin-template/admin-template.module";
import {AuthInterceptorService} from "./core/auth/auth-interceptor.service";
import {AuthService} from "./core/auth/auth.service";
import {AuthTemplateModule} from "./template/auth-template/auth-template.module";
import {NgOtpInputModule} from "ng-otp-input";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    AdminTemplateModule,
    AuthTemplateModule,
    NgOtpInputModule
  ],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    },
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
