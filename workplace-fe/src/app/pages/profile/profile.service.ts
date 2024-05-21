import {Injectable} from "@angular/core";
import {HttpService} from "../../core/http/http.service";
import ApiBuilderUtils from "../../core/api/api-builder.service";
import {Profile} from "./builder/my-decks/profile";

@Injectable()
export class ProfileService {

  constructor(private http: HttpService) {
  }

  instance = "profile";

  createProfile = () => {
    return this.http.doGet<string>(ApiBuilderUtils.buildAPI(this.instance, ["create"]));
  }

  saveProfileUrl = (url: string) => {
    return this.http.doPost<void>(ApiBuilderUtils.buildAPI(this.instance, ["save/url"]), url);
  }

  getProfile = () => {
    return this.http.doGet<Profile>(ApiBuilderUtils.buildAPI(this.instance, ["get"]));
  }

  getMyImageURLByName = (profileId: string, name: string) => {
    return this.http.doGet<any>(ApiBuilderUtils.buildAPI(this.instance, ["public", profileId, "image", name]))
  }
}
