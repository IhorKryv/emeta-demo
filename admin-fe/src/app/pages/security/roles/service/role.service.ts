import {Injectable} from '@angular/core';
import {HttpService} from "../../../../core/http/http.service";
import {Role} from "../model/role";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";
import {Parameter} from "../../../../core/model/parameter";
import {PageObject} from "../../../../../common/components/pagination/page-object";

@Injectable()
export class RoleService {

  constructor(private http: HttpService) {}

  instance = "role";

  public createRole = (role: Role) => {
    return this.http.doPost<Role>(ApiBuilderUtils.buildAPI(this.instance, ["create"]), role);
  }

  public updateRole = (id: string, role: Role) => {
    return this.http.doPut<Role>(ApiBuilderUtils.buildAPI(this.instance, ["update", id]), role);
  }

  public getSingleRole = (id: string) => {
    return this.http.doGet<Role>(ApiBuilderUtils.buildAPI(this.instance, ["get", id]));
  }

  public getAllRoles = (searchText: string = "", page: number = 0) => {
    return this.http.doGet<PageObject<Role>>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"], ApiBuilderUtils.getDefaultParams(searchText, page)));
  }

  public getRolesBySearchValue = (searchValue: string) => {
    return this.http.doGet<Role[]>(ApiBuilderUtils.buildAPI(this.instance, ["get/search"], [new Parameter("searchValue", searchValue)]));
  }

  public deleteRole = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id]));
  }
}
