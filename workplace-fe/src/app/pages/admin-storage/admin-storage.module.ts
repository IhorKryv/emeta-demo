import {NgModule} from '@angular/core';
import {ConfirmationModule} from "../../../common/components/confirmation/confirmation.module";
import {MatSelectModule} from "@angular/material/select";
import {MatIconModule} from "@angular/material/icon";
import {CommonModule} from "@angular/common";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatChipsModule} from "@angular/material/chips";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatTableModule} from "@angular/material/table";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {NotificationModule} from "../../../common/components/notification/notification.module";
import {MatDialogModule} from "@angular/material/dialog";
import {MatCardModule} from "@angular/material/card";
import {MatTabsModule} from "@angular/material/tabs";
import {SearchFieldModule} from "../../../common/components/search-field/search-field.module";
import {MatButtonModule} from "@angular/material/button";
import {AdminStorageComponent} from "./admin-storage.component";
import {AdminDecksViewComponent} from "./decks-view/admin-decks-view.component";
import {AdminStorageRoutingModule} from "./admin-storage-routing.module";
import {AdminDeckPreviewComponent} from "../../../common/components/admin-deck-preview/admin-deck-preview.component";
import {AdminDeckPreviewModule} from "../../../common/components/admin-deck-preview/admin-deck-preview.module";
import {AdminBoardsViewComponent} from "./boards-view/admin-boards-view.component";
import {AdminBoardPreviewModule} from "../../../common/components/admin-board-preview/admin-board-preview.module";
import {LoaderModule} from "../../../common/components/loader/loader.module";
import {DeckFromAdminPreviewComponent} from "./deck-from-admin-preview/deck-from-admin-preview.component";

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
    AdminStorageRoutingModule,
    MatCardModule,
    AdminDeckPreviewModule,
    AdminBoardPreviewModule,
    LoaderModule
  ],
  exports: [],
  declarations: [
    AdminStorageComponent,
    AdminDecksViewComponent,
    AdminBoardsViewComponent,
    DeckFromAdminPreviewComponent
  ],
  providers: [],
})
export class AdminStorageModule {
}
