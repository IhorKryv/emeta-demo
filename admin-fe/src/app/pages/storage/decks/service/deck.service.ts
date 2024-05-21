import {Injectable} from '@angular/core';
import {HttpService} from "../../../../core/http/http.service";
import {Deck} from "../model/deck";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";
import {PageObject} from "../../../../../common/components/pagination/page-object";
import {Card} from "../../cards/model/card";
import {A} from "@angular/cdk/keycodes";

@Injectable()
export class DeckService {

  constructor(private http: HttpService) {}

  instance = "deck";

  createDeck = (deck: Deck) => {
    return this.http.doPost<Deck>(ApiBuilderUtils.buildAPI(this.instance, ["create"]), deck);
  }

  addCardsToDeck = (id: string, formData: any) => {
    return this.http.doPost<void>(ApiBuilderUtils.buildAPI(this.instance, [id, "card/add"]), formData);
  }

  updateDeck = (id: string, deck: Deck) => {
    return this.http.doPut<Deck>(ApiBuilderUtils.buildAPI(this.instance, ["update", id]), deck);
  }

  updateDeckCardBack = (id: string, formData: any) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["update", id, "cardback"]), formData);
  }

  makeCardAsCardBack = (deckId: string, cardId: string, extension: string) => {
    return this.http.doGet<void>(ApiBuilderUtils.buildAPI(this.instance, [deckId, "swap", cardId, extension]));
  }

  getSingleDeck = (id: string) => {
    return this.http.doGet<Deck>(ApiBuilderUtils.buildAPI(this.instance, ["get", id]));
  }

  getAllDecks = (searchText: string, page: number) => {
    return this.http.doGet<PageObject<Deck>>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"], ApiBuilderUtils.getDefaultParams(searchText, page)));
  }

  getAllCardsFromDeck = (id: string, searchText: string, page: number, size: number) => {
    return this.http.doGet<PageObject<Card>>(ApiBuilderUtils.buildAPI(this.instance, [id, "card/all"], ApiBuilderUtils.getDefaultParams(searchText, page, size)));
  }

  deleteDeck = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id]));
  }

  removeCard = (deckId: string, cardId: string, extension: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, [deckId, "card/remove", cardId, extension]));
  }

  removeCardbackFromDeck = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id, "cardback"]))
  }
}
