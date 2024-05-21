import {NgModule} from '@angular/core';

import {StorageComponent} from './storage.component';
import {DragDirective} from "../../../common/components/dragDrop/dragDrop.directive";
import {AddDeckDialogComponent} from "./add-deck-dialog/add-deck.component";
import {MyDecksComponent} from "./my-decks/my-decks.component";
import {ConfirmationModule} from "../../../common/components/confirmation/confirmation.module";
import {MatSelectModule} from "@angular/material/select";
import {StorageRoutingModule} from "./storage-routing.module";
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
import {CardsComponent} from "./cards/cards.component";
import {MyBoardComponent} from "./my-boards/my-board.component";
import {AddBoardDialogComponent} from "./add-board-dialog/add-board.component";
import {AddBoardImageDialogComponent} from "./add-board-image-dialog/add-board-image.component";
import {DecksComponent} from "./decks/decks.component";
import {AdminCardsComponent} from "./admin-cards/admin-cards.component";
import {EditAdminDeckComponent} from "./edit-admin-deck-dialog/edit-admin-deck.component";
import {AdminDeckPreviewComponent} from "../../../common/components/admin-deck-preview/admin-deck-preview.component";
import {AdminDeckPreviewModule} from "../../../common/components/admin-deck-preview/admin-deck-preview.module";
import {AdminBoardPreviewModule} from "../../../common/components/admin-board-preview/admin-board-preview.module";
import {EditAdminBoardComponent} from "./edit-admin-board-dialog/edit-admin-board.component";
import {BoardsComponent} from "./boards/boards.component";
import {LoaderModule} from "../../../common/components/loader/loader.module";
import {DeckPreviewComponent} from "./deck-preview/deck-preview.component";
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
    StorageRoutingModule,
    MatCardModule,
    AdminDeckPreviewModule,
    AdminBoardPreviewModule,
    LoaderModule,
    DragDropModule
  ],
  exports: [],
  declarations: [
    StorageComponent,
    AddDeckDialogComponent,
    AddBoardDialogComponent,
    AddBoardImageDialogComponent,
    EditAdminDeckComponent,
    EditAdminBoardComponent,
    MyDecksComponent,
    BoardsComponent,
    DecksComponent,
    CardsComponent,
    AdminCardsComponent,
    MyBoardComponent,
    DeckPreviewComponent
  ],
  providers: [],
})
export class StorageModule {
}
