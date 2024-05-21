import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgModule} from "@angular/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatTabsModule} from "@angular/material/tabs";
import {MatSelectModule} from "@angular/material/select";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatCardModule} from "@angular/material/card";
import {AuthVerifyDialogComponent} from "./auth-verify-dialog/auth-verify-dialog.component";
import {NgOtpInputModule} from "ng-otp-input";
import {MatListModule} from "@angular/material/list";
import {LoginComponent} from "./login/login.component";
import {AuthService} from "./service/auth.service";
import {RegistrationComponent} from "./registration/registration.component";
import {AuthRoutingModule} from "./auth-routing-module";
import {AuthComponent} from "./auth.component";
import {GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule} from "@abacritt/angularx-social-login";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
import {LoaderModule} from "../../../common/components/loader/loader.module";
import {RegisterCompleteComponent} from "./register-complete/register-complete.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatDialogModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatTabsModule,
    MatSelectModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatCardModule,
    MatListModule,
    NgOtpInputModule,
    AuthRoutingModule,
    SocialLoginModule,
    LoaderModule
  ],
  exports: [],
  declarations: [
    LoginComponent,
    RegistrationComponent,
    AuthComponent,
    AuthVerifyDialogComponent,
    ResetPasswordComponent,
    RegisterCompleteComponent
  ],
  providers: [
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '43888369889-uov74t76p4rrv73tblan43qrqm7ak0rh.apps.googleusercontent.com'
            )
          },
        ],
        onError: (err) => {
          console.error(err);
        }
      } as SocialAuthServiceConfig,
    }
  ],
})
export class AuthModule { }
