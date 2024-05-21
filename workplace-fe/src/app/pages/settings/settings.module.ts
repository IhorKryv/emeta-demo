import {NgModule} from '@angular/core';

import {SettingsComponent} from './settings.component';
import {UserService} from "../auth/registration/service/user.service";
import {ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {DatePipe, NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";

@NgModule({
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    NgIf,
    MatButtonModule,
    MatIconModule,
    DatePipe
  ],
  exports: [],
  declarations: [SettingsComponent],
  providers: [UserService],
})
export class SettingsModule {
}
