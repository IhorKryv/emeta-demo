import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpService} from "../../../core/http/http.service";
import {AuthService} from "../service/auth.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {LoginRequest} from "../model/loginRequest";
import {AuthVerifyDialogComponent} from "../auth-verify-dialog/auth-verify-dialog.component";
import {VerificationRequest} from "../model/verificationRequest";
import {SocialAuthService} from "@abacritt/angularx-social-login";

@Component({
  selector: 'emeta-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {
  constructor(private fb: FormBuilder, private httpService: HttpService,
              private authService: AuthService, public dialog: MatDialog,
              private route: Router, private sanitizer: DomSanitizer,
              private socialAuthService: SocialAuthService) {
  }

  login: LoginRequest = new LoginRequest();
  loginError: boolean = false;
  loginErrorMessage: string = "";
  badLogin: boolean = false;
  submit = false;
  qrCodeUrl?: SafeResourceUrl;

  emailForPassword: string = "";
  code: number | undefined;
  verificationRequest: VerificationRequest = new VerificationRequest();

  user: any;
  loggedIn: any;
  showLoader: boolean = false;
  actionStarted: boolean = false;

  openVerifyAuth(requestedData: any) {
    const dialogRef = this.dialog.open(AuthVerifyDialogComponent, {
      data: {
        qrCodeUrl: this.qrCodeUrl,
        login: requestedData
      },
      width: '40vw'
    });

    dialogRef.afterClosed().subscribe((authorized: boolean) => {
      if (authorized) {
        this.route.navigate(['../workplace'])
      }
    });
  }

  submitForm(): void {
    for (const i in this.authForm.controls) {
      this.authForm.get('userName')?.markAsDirty();
      this.authForm.get('password')?.markAsDirty();
    }
    if (this.authForm.valid) {
      this.loginError = false;
      this.showLoader = true;
      this.actionStarted = true;
      this.login.username = this.authForm.controls['userName'].value!
      this.login.password = this.authForm.controls['password'].value!
      this.authService.login(this.login.username, this.login.password, this.showError).subscribe({
        next:(resp) => {
          this.route.navigate(['../workplace'])
          this.authService.updateData(resp)
          this.actionStarted = false;
          this.showLoader = false
          this.loginError = false;
        },
        error: err => {
          console.log(err.error)
          this.actionStarted = false;
          this.showLoader = false;
          this.loginError = true;
          if (err.error.status === 403) {
            this.loginErrorMessage = $localize`:@@login-errorMessage-notAvailable:Your workplace has been deactivated. Please, contact with administration for more information.`;
          } else {
            this.loginErrorMessage = $localize`:@@login-errorMessage-notFound:There is no user with current username and password. Please, check your credentials and try again.`;
          }
        }
      })
    }
  }

  showError = () => {
    this.badLogin = true
  }

  // public openVerificationDialog = (isFirstLogin: boolean, googleLoginRequest?: LoginRequest) => {
  //   let requestData = googleLoginRequest ? googleLoginRequest: this.login;
  //   if (isFirstLogin) {
  //     this.authService.generateQrCode(requestData).subscribe({
  //       next: resp => {
  //         this.qrCodeUrl = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + resp.qrCodeUrl)
  //         this.openVerifyAuth(requestData)
  //       }, error: err => {
  //         console.log(err.error.error)
  //       }
  //     })
  //   } else {
  //     this.openVerifyAuth(requestData)
  //   }
  // }

  public authForm = new FormGroup({
    userName: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  })

  ngOnInit() {
    this.socialAuthService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);
      this.authService.loginWithGoogle(user).subscribe({
        next: resp => {
          // let loginRequest = new LoginRequest();
          // loginRequest.username = user.email;
          // loginRequest.password = undefined;
          this.authService.updateData(resp)
          this.route.navigate(['../workplace'])
          // this.openVerificationDialog(resp.firstLogin!, loginRequest)
        },
        error: (err => console.log(err))
      })
    });
  }

  failLoginMessage = () => {
    this.badLogin = true;
  }
}
