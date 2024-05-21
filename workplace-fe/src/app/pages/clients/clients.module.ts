import {NgModule} from '@angular/core';

import {ClientsComponent} from './clients.component';
import {SearchFieldModule} from "../../../common/components/search-field/search-field.module";
import {ConfirmationModule} from "../../../common/components/confirmation/confirmation.module";
import {NotificationModule} from "../../../common/components/notification/notification.module";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatTabsModule} from "@angular/material/tabs";
import {CommonModule} from "@angular/common";
import {MatChipsModule} from "@angular/material/chips";
import {MatSelectModule} from "@angular/material/select";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatGridListModule} from "@angular/material/grid-list";
import {StorageRoutingModule} from "../storage/storage-routing.module";
import {MatCardModule} from "@angular/material/card";
import {AddClientComponent} from "./add-client-dialog/add-client.component";
import {ClientsListComponent} from "./clients-list/clients-list.component";
import {ClientsRoutingModule} from "./clients-routing.module";
import {LoaderModule} from "../../../common/components/loader/loader.module";
import {ClientRecordsComponent} from "./client-records-list/client-records.component";
import {AddClientRecordComponent} from "./add-client-record-dialog/add-client-record.component";
import {DragDirective} from "../../../common/components/dragDrop/dragDrop.directive";
import {DragDropModule} from "../../../common/components/dragDrop/dragDrop.module";

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
    MatPaginatorModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    MatCheckboxModule,
    MatGridListModule,
    ClientsRoutingModule,
    MatCardModule,
    LoaderModule,
    DragDropModule
  ],
  exports: [],
  declarations: [
    ClientsComponent,
    ClientsListComponent,
    AddClientComponent,
    ClientRecordsComponent,
    AddClientRecordComponent
  ],
  providers: [],
})
export class ClientsModule {

}
