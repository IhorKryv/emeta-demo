import {ChangeDetectorRef, Component, Inject, ViewChild} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {VerificationRequest} from "../model/verificationRequest";
import {NgOtpInputComponent, NgOtpInputConfig} from "ng-otp-input";
import {AuthService} from "../../../core/auth/auth.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {NotificationType} from "../../../../common/components/notification/notification-type";

@Component({
  selector: 'auth-verify-dialog',
  templateUrl: 'auth-verify-dialog.component.html',
  styleUrls: ['auth-verify-dialog.component.scss']
})

export class AuthVerifyDialogComponent {
  constructor(public dialogRef: MatDialogRef<AuthVerifyDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private authService: AuthService,
              private notificationService: NotificationService, private changeDetectorRef: ChangeDetectorRef) {
    this.qrCode = data.qrCodeUrl;
  }

  qrCode: any;
  verificationRequest: VerificationRequest = new VerificationRequest();
  otp?: string;
  showOtpComponent = true;
  @ViewChild(NgOtpInputComponent, {static: false}) ngOtpInput?: NgOtpInputComponent;
  @ViewChild('otpInput') otpInput!: NgOtpInputComponent;
  config: NgOtpInputConfig = {
    allowNumbersOnly: true,
    length: 6,
    isPasswordInput: false,
    disableAutoFocus: false,
    placeholder: ''
  };

  onOtpChange(otp: string) {
    const codeControl = this.verificationForm.get('code');
    codeControl?.setValue(otp);
    if (otp.length === 6) {
      this.validate();
      this.otpInput.setValue(null);
      this.changeDetectorRef.detectChanges();
    }
  }


  public verificationForm = new FormGroup({
    userName: new FormControl({value: this.data.login.username, disabled: true}),
    code: new FormControl('', Validators.required)
  })

  validate() {
    if (this.verificationForm.invalid) {
      this.verificationForm.get('code')?.markAsTouched();
      return;
    }
    if (this.verificationForm.valid) {
      this.verificationRequest.username = this.data.login.username
      this.verificationRequest.password = this.data.login.password
      this.verificationRequest.code = this.verificationForm.get('code')?.value!
      this.authService.verifyAccount(this.verificationRequest).subscribe({
        next: (resp) => {
          this.authService.updateData(resp)
          this.dialogRef.close(true)
        },
        error: err => {
          this.notificationService.showNotification(NotificationType.ERROR, err.error.error)
        }
      })
    }
  }

  onClose() {
    this.dialogRef.close();
  }

  generateCode() {
    this.data.generateCode(true);
    this.dialogRef.close();
  }
}
