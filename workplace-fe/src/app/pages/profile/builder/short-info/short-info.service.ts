import {Injectable} from '@angular/core';
import {HttpService} from "../../../../core/http/http.service";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";
import {ShortInfo} from "./short-info";

@Injectable()
export class ShortInfoService {

  constructor(private http: HttpService) {
  }

  instance = "profile/short-info";

  getShortInfo = () => {
    return this.http.doGet<ShortInfo>(ApiBuilderUtils.buildAPI(this.instance, ["my"]));
  }

  saveTitle = (value: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/title"]), value);
  }

  saveDescription = (value: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/description"]), value);
  }

  saveCardIcon = (value: string, cardId: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/card", cardId, "icon"]), value);
  }

  saveCardTitle = (value: string, cardId: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/card", cardId, "title"]), value);
  }

  saveCardText = (value: string, cardId: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/card", cardId, "text"]), value);
  }
}
