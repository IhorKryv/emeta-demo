import {Injectable} from '@angular/core';
import {HttpService} from "../../../../core/http/http.service";
import {Schedule} from "./schedule";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";

@Injectable()
export class ScheduleService {

  constructor(private http: HttpService) {
  }

  instance = "profile/schedule";

  getSchedule = () => {
    return this.http.doGet<Schedule>(ApiBuilderUtils.buildAPI(this.instance, ["my"]));
  }

  saveTitle = (title: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/title"]), title);
  }

  saveShortText = (shortText: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/short-text"]), shortText);
  }

  saveScheduleItem = (key: string, value: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/item", key]), value);
  }
}
