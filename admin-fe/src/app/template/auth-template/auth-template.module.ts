import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthTemplateComponent} from "./auth-template.component";
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
import {AuthService} from "../../core/auth/auth.service";
import {MatListModule} from "@angular/material/list";
import {NotificationModule} from "../../../common/components/notification/notification.module";

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
    NotificationModule

  ],
  exports: [],
  declarations: [
    AuthTemplateComponent,
    AuthVerifyDialogComponent
  ],
  providers: [
    AuthService,
  ],
})
export class AuthTemplateModule { }
