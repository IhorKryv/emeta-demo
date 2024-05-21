import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {MatDividerModule} from "@angular/material/divider";
import {MatIconModule} from "@angular/material/icon";
import {AlertModule} from "../common/components/alert/alert.module";
import {AutocompleteModule} from "../common/components/autocomplete/autocomplete.module";
import {SearchFieldModule} from "../common/components/search-field/search-field.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptorService} from "./core/auth/auth-interceptor.service";
import {AppRoutingModule} from "./app-routing.module";
import {WorkplaceTemplateModule} from "./template/workplace-template/workplace-template.module";
import {CommonModule} from "@angular/common";
import {NgOtpInputModule} from "ng-otp-input";
import {RouterOutlet} from "@angular/router";
import {AuthService} from "./pages/auth/service/auth.service";
import {FullProfileService} from "./pages/full-profile/full-profile.service";
import {PlanService} from "./pages/plan-selector/plan.service";
import {PlanSelectorModule} from "./pages/plan-selector/plan-selector.module";


@NgModule({
  declarations: [
    AppComponent
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AlertModule,
        MatButtonModule,
        MatDividerModule,
        MatIconModule,
        AutocompleteModule,
        SearchFieldModule,
        RouterOutlet,
        AppRoutingModule,
        CommonModule,
        HttpClientModule,
        WorkplaceTemplateModule,
        NgOtpInputModule
    ],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    },
    FullProfileService,
    PlanService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
