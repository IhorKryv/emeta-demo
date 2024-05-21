import {NgModule} from '@angular/core';

import {SessionsComponent} from './sessions.component';
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
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatCardModule} from "@angular/material/card";
import {ArchivedSessionsComponent} from "./archived/archived-sessions.component";
import {AvailableSessionsComponent} from "./available/available-sessions.component";
import {SessionsRoutingModule} from "./sessions-routing.module";
import {AddSessionDialogComponent} from "./add-session-dialog/add-session.component";
import {MatNativeDateModule} from "@angular/material/core";
import {AddClientDialogComponent} from "./add-client-dialog/add-client.component";
import {SessionInfoDialogComponent} from "./session-info-dialog/session-info.component";
import {LoaderModule} from "../../../common/components/loader/loader.module";
import {InstantSessionComponent} from "./instant-session/instant-session.component";

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
    SessionsRoutingModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    LoaderModule
  ],
  exports: [InstantSessionComponent],
  declarations: [
    SessionsComponent,
    AddSessionDialogComponent,
    AddClientDialogComponent,
    AvailableSessionsComponent,
    ArchivedSessionsComponent,
    SessionInfoDialogComponent,
    InstantSessionComponent
  ],
  providers: [],
})
export class SessionsModule {
}
