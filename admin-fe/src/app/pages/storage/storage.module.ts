import {NgModule} from '@angular/core';

import {StorageComponent} from './storage.component';
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
import {DragDropModule} from "@angular/cdk/drag-drop";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {StorageRoutingModule} from "./storage-routing.module";
import {CategoriesComponent} from "./categories/categories.component";
import {AddCategoryDialogComponent} from "./categories/add-category-dialog/add-category.component";
import {DeckComponent} from "./decks/deck.component";
import {AddDeckDialogComponent} from "./decks/add-deck-dialog/add-deck.component";
import {CategoriesListPipe} from "./decks/categories-list.pipe";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {CardsComponent} from "./cards/cards.component";
import {MatGridListModule} from "@angular/material/grid-list";
import {DragDirective} from "../../../common/components/dragDrop/dragDrop.directive";
import {MatCardModule} from "@angular/material/card";
import {LoaderBlockModule} from "../../../common/components/loaderBlock/loader-block.module";
import {BoardComponent} from "./boards/board.component";
import {AddBoardComponent} from "./boards/add-board-dialog/add-board.component";
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
    MatSnackBarModule,
    MatCheckboxModule,
    MatGridListModule,
    StorageRoutingModule,
    MatCardModule,
    LoaderBlockModule,
    LoaderModule
  ],
  exports: [],
  declarations: [
    StorageComponent,
    CategoriesComponent,
    AddCategoryDialogComponent,
    DeckComponent,
    AddDeckDialogComponent,
    CategoriesListPipe,
    CardsComponent,
    BoardComponent,
    AddBoardComponent,
    DragDirective
  ],
  providers: [],
})
export class StorageModule {
}
