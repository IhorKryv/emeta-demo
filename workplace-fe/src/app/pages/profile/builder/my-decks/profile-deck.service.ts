import {Injectable} from '@angular/core';
import {HttpService} from "../../../../core/http/http.service";
import {ProfileDeck, ProfileDeckItem} from "./profile-deck";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";

@Injectable()
export class ProfileDeckService {

  constructor(private http: HttpService) {
  }

  instance = "profile/deck";

  getProfileDeck = () => {
    return this.http.doGet<ProfileDeck>(ApiBuilderUtils.buildAPI(this.instance, ["my"]));
  }

  saveTitle = (title: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/title"]), title)
  }

  saveInfo = (info: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/info"]), info)
  }

  addDeckItem = () => {
    return this.http.doGet<ProfileDeckItem>(ApiBuilderUtils.buildAPI(this.instance, ["my/add"]));
  }

  removeDeckItem = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/remove", id]));
  }

  saveDeckItemImage = (id: string, formDate: any) => {
    return this.http.doPut<any>(ApiBuilderUtils.buildAPI(this.instance, ["my", id, "image"]), formDate)
  }

  saveDeckItemTitle = (id: string, title: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my", id, "title"]), title)
  }

  saveDeckItemInfo = (id: string, info: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my", id, "info"]), info)
  }


}
