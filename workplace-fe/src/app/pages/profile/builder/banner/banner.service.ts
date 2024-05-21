import {Injectable} from '@angular/core';
import {HttpService} from "../../../../core/http/http.service";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";
import {Banner} from "./banner";

@Injectable()
export class BannerService {

  constructor(private http: HttpService) {
  }

  instance = "profile/banner";

  getBanner = () => {
    return this.http.doGet<Banner>(ApiBuilderUtils.buildAPI(this.instance, ["my"]));
  }

  saveTitle = (title: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/title"]), title);
  }

  saveInfo = (info: string) => {
    return this.http.doPut<void>(ApiBuilderUtils.buildAPI(this.instance, ["my/info"]), info);
  }

  saveImage = (formData: any) => {
    return this.http.doPut<any>(ApiBuilderUtils.buildAPI(this.instance, ["my/image"]), formData);
  }

  saveBackground = (formData: any) => {
    return this.http.doPut<any>(ApiBuilderUtils.buildAPI(this.instance, ["my/background"]), formData);
  }


}
