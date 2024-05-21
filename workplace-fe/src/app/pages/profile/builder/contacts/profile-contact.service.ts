import {Injectable} from "@angular/core";
import {HttpService} from "../../../../core/http/http.service";
import {ProfileDeck} from "../my-decks/profile-deck";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";
import {ProfileContact} from "./profile-contact";

@Injectable()
export class ProfileContactService {

  constructor(private http: HttpService) {
  }

  instance = "profile/contact";

  getProfileContact = () => {
    return this.http.doGet<ProfileContact>(ApiBuilderUtils.buildAPI(this.instance, ["my"]));
  }

  saveTitle = (title: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/title"]), title)
  }

  saveShortText = (shortText: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/short-text"]), shortText)
  }

  saveEmail = (email: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/email"]), email)
  }

  savePhone = (phone: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/phone"]), phone)
  }
}
