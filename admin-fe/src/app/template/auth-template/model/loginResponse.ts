export class LoginResponse {
  public username: string | undefined;
  public token: string | undefined;
  public authorities: string[] = [];
}
