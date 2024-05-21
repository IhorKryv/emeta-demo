import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {DeckService} from "./service/deck.service";
import {MyDecksComponent} from "./my-decks/my-decks.component";
import {CardsComponent} from "./cards/cards.component";
import {MyBoardComponent} from "./my-boards/my-board.component";
import {BoardService} from "./service/board.service";
import {DecksComponent} from "./decks/decks.component";
import {AdminCardsComponent} from "./admin-cards/admin-cards.component";
import {BoardsComponent} from "./boards/boards.component";
import {DeckPreviewComponent} from "./deck-preview/deck-preview.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'my-decks'},
  {path: 'my-decks', component: MyDecksComponent},
  {path: 'my-decks/:id/cards', component: CardsComponent},
  {path: 'decks', component: DecksComponent},
  {path: 'decks/:id/cards', component: DeckPreviewComponent},
  {path: 'my-boards', component: MyBoardComponent},
  {path: 'boards', component: BoardsComponent},
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
export class StorageRoutingModule {
}
