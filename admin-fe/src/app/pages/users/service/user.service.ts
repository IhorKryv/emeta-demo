import {Injectable} from '@angular/core';
import {HttpService} from "../../../core/http/http.service";
import ApiBuilderUtils from "../../../core/api/api-builder.service";
import {User} from "../model/user";
import {PageObject} from "../../../../common/components/pagination/page-object";

@Injectable()
export class UserService {

  constructor(private http: HttpService) {}

  instance = "user"

  public createUser = (user: User) => {
    return this.http.doPost<User>(ApiBuilderUtils.buildAPI(this.instance, ['create']), user);
  }

  public updateUser = (id: string, user: User) => {
    return this.http.doPut<User>(ApiBuilderUtils.buildAPI(this.instance, ['update', id]), user);
  }

  public getUserById = (id: string) => {
    return this.http.doGet<User>(ApiBuilderUtils.buildAPI(this.instance, ['get', id]));
  }

  public getAllUsers = (searchText: string = "", page: number = 0) => {
    return this.http.doGet<PageObject<User>>(ApiBuilderUtils.buildAPI(this.instance, ['get/all'], ApiBuilderUtils.getDefaultParams(searchText, page)));
  }

  public deleteUser = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ['delete', id]));
  }


}
