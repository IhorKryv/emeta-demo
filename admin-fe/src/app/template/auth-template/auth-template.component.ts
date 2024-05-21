import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpService} from "../../core/http/http.service";
import {LoginRequest} from "./model/loginRequest";
import {VerificationRequest} from "./model/verificationRequest";
import {MatDialog} from "@angular/material/dialog";
import {AuthVerifyDialogComponent} from "./auth-verify-dialog/auth-verify-dialog.component";
import {Router} from "@angular/router";
import {AuthService} from "../../core/auth/auth.service";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'emeta-login',
  templateUrl: './auth-template.component.html',
  styleUrls: ['./auth-template.component.scss']
})

export class AuthTemplateComponent implements OnInit {
  constructor(private fb: FormBuilder, private httpService: HttpService,
              private authService: AuthService, public dialog: MatDialog,
              private route: Router, private sanitizer: DomSanitizer) {
  }

  login: LoginRequest = new LoginRequest();
  badLogin: boolean = false;
  submit = false;
  qrCodeUrl?: SafeResourceUrl;

  emailForPassword: string = "";
  code: number | undefined;
  verificationRequest: VerificationRequest = new VerificationRequest();

  openVerifyAuth() {
    const dialogRef = this.dialog.open(AuthVerifyDialogComponent, {
      data: {
        qrCodeUrl: this.qrCodeUrl,
        login: this.login,
        generateCode: this.openVerificationDialog
      },
      width: '25vw'
    });

    dialogRef.afterClosed().subscribe((authorized: boolean) => {
      if (authorized) {
        this.route.navigate(['/admin'])
      }
    });
  }

  submitForm(): void {
    for (const i in this.authForm.controls) {
      this.authForm.get('userName')?.markAsDirty();
      this.authForm.get('password')?.markAsDirty();
    }
    if (this.authForm.valid) {
      this.login.username = this.authForm.controls['userName'].value!
      this.login.password = this.authForm.controls['password'].value!
      this.authService.login(this.login.username, this.login.password, this.openVerificationDialog, this.showError);
    }
  }

  showError = () => {
    this.badLogin = true
  }

  public openVerificationDialog = (isFirstLogin: boolean) => {
    if (isFirstLogin) {
      this.authService.generateQrCode(this.login).subscribe({
        next: resp => {
          this.qrCodeUrl = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + resp.qrCodeUrl)
          this.openVerifyAuth()
        }, error: err => {
          console.log(err.error.error)
        }
      })
    } else {
      this.openVerifyAuth()
    }
  }

  public authForm = new FormGroup({
    userName: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  })

  ngOnInit() {
  }

  failLoginMessage = () => {
    this.badLogin = true;
  }
}
