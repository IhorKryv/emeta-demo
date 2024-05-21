import {Injectable} from "@angular/core";
import {HttpService} from "../../../../core/http/http.service";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";
import {Permission} from "../model/permission";

@Injectable()
export class PermissionService {
  constructor(private http: HttpService) {}
  instance = "authority";

  getAllAuthorities = () => {
    return this.http.doGet<Permission[]>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"]));
  }
}
