import {Injectable} from "@angular/core";
import {User} from "../model/user";
import {HttpService} from "../../../../core/http/http.service";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";
import {UpdatePasswordRequest} from "../../model/updatePassword";

@Injectable()
export class UserService {

  constructor(private http: HttpService) {}

  instance = "user"

  public updateUser = (id: string, user: User) => {
    return this.http.doPut<User>(ApiBuilderUtils.buildAPI(this.instance, ['update', id]), user);
  }

  public updateUserProfile = (id: string, user: User) => {
    return this.http.doPut<User>(ApiBuilderUtils.buildAPI(this.instance, ['update', id, 'profile']), user);
  }

  public updateUserPassword = (id: string, request: UpdatePasswordRequest) => {
    return this.http.doPut<User>(ApiBuilderUtils.buildAPI(this.instance, ['update', id, 'profile/password']), request);
  }

  public getUserById = (id: string) => {
    return this.http.doGet<User>(ApiBuilderUtils.buildAPI(this.instance, ['get', id]));
  }

  public deleteUser = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ['delete', id]));
  }

  public getCurrent = () => {
    return this.http.doGet<User>(ApiBuilderUtils.buildAPI(this.instance, ['get/my']));
  }


}
