import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {CategoriesComponent} from "./categories/categories.component";
import {CategoryService} from "./categories/service/category.service";
import {DeckService} from "./decks/service/deck.service";
import {DeckComponent} from "./decks/deck.component";
import {CardsComponent} from "./cards/cards.component";
import {FileService} from "../../../common/components/file/file.service";
import {BoardComponent} from "./boards/board.component";
import {BoardService} from "./boards/service/board.service";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'categories'},
  {path: 'categories', component: CategoriesComponent},
  {path: 'decks', component: DeckComponent},
  {path: 'decks/:id/cards', component: CardsComponent},
  {path: 'boards', component: BoardComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  declarations: [],
  providers: [
    CategoryService,
    DeckService,
    BoardService,
    FileService
  ],
})
export class StorageRoutingModule {
}
