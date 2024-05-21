export class User {
  id: string | undefined;
  username: string | undefined;
  password: string | undefined;
  firstName: string | undefined;
  lastName: string | undefined;
  phone: string | undefined;
  createdAt: Date = new Date();
  roles: string[] = [];
}
