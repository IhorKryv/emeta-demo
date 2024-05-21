import {NgModule} from '@angular/core';
import {UserListComponent} from "./user-list/user-list.component";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatTabsModule} from "@angular/material/tabs";
import {MatSelectModule} from "@angular/material/select";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatChipsModule} from "@angular/material/chips";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {SearchFieldModule} from "../../../common/components/search-field/search-field.module";
import {ConfirmationModule} from "../../../common/components/confirmation/confirmation.module";
import {UsersComponent} from "./users.component";
import {UsersRoutingModule} from "./users-routing.module";
import {AddUserComponent} from "./add-user-dialog/add-user.component";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {NotificationModule} from "../../../common/components/notification/notification.module";
import {LoaderModule} from "../../../common/components/loader/loader.module";


@NgModule({
  imports: [
    SearchFieldModule,
    ConfirmationModule,
    NotificationModule,
    MatDialogModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatTableModule,
    MatIconModule,
    MatTabsModule,
    CommonModule,
    MatChipsModule,
    MatSelectModule,
    MatAutocompleteModule,
    DragDropModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    UsersRoutingModule,
    MatSnackBarModule,
    LoaderModule
  ],
  exports: [],
  declarations: [
    UsersComponent,
    UserListComponent,
    AddUserComponent
  ],
  providers: [],
})
export class UsersModule {
}
