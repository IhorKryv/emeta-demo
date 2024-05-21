import {Injectable} from '@angular/core';
import {HttpService} from "../../../core/http/http.service";
import ApiBuilderUtils from "../../../core/api/api-builder.service";

@Injectable()
export class ResetPasswordService {

  constructor(private http: HttpService) {
  }

  instance: string = "password";

  firstStep = (username: string) => {
    return this.http.doPost<any>(ApiBuilderUtils.buildAPI(this.instance, ["create"]), {username: username})
  }

  secondStep = (username: string, code: string) => {
    return this.http.doPost<any>(ApiBuilderUtils.buildAPI(this.instance, ["validate"]), {username: username, code: code})
  }

  thirdStep = (username: string, password: string) => {
    return this.http.doPost<void>(ApiBuilderUtils.buildAPI(this.instance, ["change"]), {username: username, newPassword: password})
  }
}
