export class User {
  id: string | undefined;
  username: string | undefined;
  firstName: string | undefined;
  lastName: string | undefined;
  createdAt: Date = new Date();
  roles: string[] = [];
}
