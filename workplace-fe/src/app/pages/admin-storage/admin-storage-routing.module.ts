import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {DeckService} from "../storage/service/deck.service";
import {AdminDecksViewComponent} from "./decks-view/admin-decks-view.component";
import {AdminBoardsViewComponent} from "./boards-view/admin-boards-view.component";
import {BoardService} from "../storage/service/board.service";
import {DeckFromAdminPreviewComponent} from "./deck-from-admin-preview/deck-from-admin-preview.component";


const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'decks'},
  {path: 'decks', component: AdminDecksViewComponent},
  {path: 'decks/:id', component: DeckFromAdminPreviewComponent},
  {path: 'boards', component: AdminBoardsViewComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  declarations: [],
  providers: [
    DeckService,
    BoardService
  ],
})
export class AdminStorageRoutingModule {
}
