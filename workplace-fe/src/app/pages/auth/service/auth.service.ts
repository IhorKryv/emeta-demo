import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {HttpService} from "../../../core/http/http.service";
import {HttpClient} from "@angular/common/http";
import {UserData} from "../../../core/security/user-data";
import {VerificationRequest} from "../model/verificationRequest";
import {LoginResponse} from "../model/loginResponse";
import {LoginRequest} from "../model/loginRequest";
import {QrCode} from "../model/qrCode";
import ApiBuilderUtils from "../../../core/api/api-builder.service";
import {User} from "../registration/model/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router, private httpService: HttpClient, private customHttp: HttpService) {
    // This is intentional
  }

  getAuthToken(): string | null {
    return localStorage.getItem('token')
  }

  userData: UserData | undefined;
  loggedIn: boolean = false;

  private AUTH_REDIRECT = 'auth';

  public verifyAccount = (verificationRequest: VerificationRequest) => {
    return this.customHttp.doPost<LoginResponse>(ApiBuilderUtils.buildAPI('auth',['login-confirm']),
      verificationRequest);
  }

  public loginWithUserNameAndPassword = (login: string | undefined, password: string | undefined) => {
    return this.httpService.post<LoginResponse>(ApiBuilderUtils.buildAPI('auth',['login']), {
      "username": login,
      "password": password,
      "supportCookie": true
    });
  }

  public loginWithGoogle = (user: any) => {
    return this.httpService.post<LoginResponse>(ApiBuilderUtils.buildAPI('auth',['google-login']), user);
  }

  public generateQrCode = (loginRequest: LoginRequest) => {
    return this.httpService.post<QrCode>(ApiBuilderUtils.buildAPI('auth',['generate-code']), loginRequest);
  }

  public updateData = (resp: UserData) => {
    this.userData = resp;
    this.loggedIn = (this.userData !== undefined);
    if (typeof this.userData.token === "string") {
      localStorage.setItem('token', this.userData.token);
    }
  }

  validateToken = (token: string) => {
    return this.httpService.post<UserData>(ApiBuilderUtils.buildAPI("auth", ["validate"]), token);
  }

  redirectToLogin() {
    this.logout();
  }

  public login = (login: string | undefined, password: string | undefined, errorFn: Function) => {
    return this.loginWithUserNameAndPassword(login, password)
  }

  public signUp = (user: User) => {
    return this.customHttp.doPost<User>(ApiBuilderUtils.buildAPI('auth', ['sign-up']), user);
  }

  logout(router?: Router) {
    this.userData = undefined;
    this.loggedIn = false;
    localStorage.removeItem('token');
    if (!this.router && router) {
      router.navigate(['/auth']);
    } else {
      this.router.navigate(['/auth']);
    }
  }

}
