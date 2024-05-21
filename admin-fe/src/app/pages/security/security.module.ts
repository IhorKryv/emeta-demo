import {NgModule} from '@angular/core';

import {SecurityComponent} from './security.component';
import {PermissionsComponent} from "./permissions/permissions.component";
import {RolesComponent} from "./roles/roles.component";
import {AddRolesDialogComponent} from "./add-roles-dialog/add-roles.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatTabsModule} from "@angular/material/tabs";
import {SecurityRoutingModule} from "./security-routing.module";
import {MatSelectModule} from "@angular/material/select";
import {CommonModule} from "@angular/common";
import {NotificationModule} from "../../../common/components/notification/notification.module";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {SearchFieldModule} from "../../../common/components/search-field/search-field.module";
import {ConfirmationModule} from "../../../common/components/confirmation/confirmation.module";
import {MatPaginatorModule} from "@angular/material/paginator";
import {LoaderModule} from "../../../common/components/loader/loader.module";

@NgModule({
  imports: [
    SecurityRoutingModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NotificationModule,
    SearchFieldModule,
    ConfirmationModule,
    MatFormFieldModule,
    MatDialogModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatTabsModule,
    MatSelectModule,
    MatSnackBarModule,
    MatPaginatorModule,
    LoaderModule
  ],
  exports: [SecurityComponent],
  declarations: [
    SecurityComponent,
    PermissionsComponent,
    RolesComponent,
    AddRolesDialogComponent
  ],
  providers: [],
})
export class SecurityModule {
}
