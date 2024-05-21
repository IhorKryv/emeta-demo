import {Injectable} from '@angular/core';
import {HttpService} from "../../../core/http/http.service";
import {Deck} from "../model/deck";
import ApiBuilderUtils from "../../../core/api/api-builder.service";
import {PageObject} from "../../../../common/components/pagination/page-object";
import {Card} from "../model/card";

@Injectable()
export class DeckService {

  constructor(private http: HttpService) {
  }

  instance = "user-deck";
  adminInstance = "admin-decks"

  createDeck = (deck: Deck) => {
    return this.http.doPost<Deck>(ApiBuilderUtils.buildAPI(this.instance, ["create"]), deck);
  }

  addCardsToDeck = (deckId: string, formData: any) => {
    return this.http.doPost<void>(ApiBuilderUtils.buildAPI(this.instance, [deckId, "card/add"]), formData);
  }

  addAdminDeck = (deck: Deck) => {
    return this.http.doPost<void>(ApiBuilderUtils.buildAPI(this.adminInstance, ["add"]), deck);
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

  updateAdminDeck = (id: string, deck: Deck) => {
    return this.http.doPut<Deck>(ApiBuilderUtils.buildAPI(this.adminInstance, ["update", id]), deck);
  }

  getSingleDeck = (id: string) => {
    return this.http.doGet<Deck>(ApiBuilderUtils.buildAPI(this.instance, ["get", id]))
  }

  getAllDecks = () => {
    return this.http.doGet<Deck[]>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"]));
  }

  getAllCardsFromDeck = (id: string, searchText: string, page: number, size: number) => {
    return this.http.doGet<PageObject<Card>>(ApiBuilderUtils.buildAPI(this.instance, [id, "card/all"], ApiBuilderUtils.getDefaultParams(searchText, page, size)));
  }

  getAllDecksFromAdmin = () => {
    return this.http.doGet<Deck[]>(ApiBuilderUtils.buildAPI(this.adminInstance, ["all"]));
  }

  getSingleDeckFromAdmin = (deckId: string) => {
    return this.http.doGet<Deck>(ApiBuilderUtils.buildAPI(this.adminInstance, [deckId]))
  }

  getAllSelectedAdminDecks = () => {
    return this.http.doGet<Deck[]>(ApiBuilderUtils.buildAPI(this.adminInstance, ["selected"]));
  }

  getAllCardsForAdminDeck = (deckId: string) => {
    return this.http.doGet<Card[]>(ApiBuilderUtils.buildAPI(this.adminInstance, [deckId, "cards"]));
  }

  getAdminDeckStats = () => {
    return this.http.doGet<any>(ApiBuilderUtils.buildAPI(this.adminInstance, ["stats"]));
  }

  deleteDeck = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id]));
  }

  removeCard = (deckId: string, cardId: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, [deckId, "card/remove", cardId]));
  }

  removeCardbackFromDeck = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id, "cardback"]));
  }

  removeAdminDeckFromCollection = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.adminInstance, ["remove", id]));
  }
}
