import {User} from "../../../auth/registration/model/user";

export class Profile {
  id: string | undefined;
  url: string | undefined;
  user: User = new User();
}
