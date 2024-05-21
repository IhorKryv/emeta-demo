import {Injectable} from '@angular/core';
import {HttpService} from "../../core/http/http.service";
import {FullProfile} from "./full-profile";
import {environment} from "../../../environments/environment";

@Injectable()
export class FullProfileService {

  constructor(private http: HttpService) {
  }

  getFullProfile = (id: string) => {
    return this.http.doGet<FullProfile>(environment.serverURL + "public/profile/" + id);
  }

}
