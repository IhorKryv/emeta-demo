import {Component, OnInit} from '@angular/core';
import {ResetPasswordService} from "../service/reset-password.service";
import {
  AbstractControl,
  FormControl,
  FormGroup,
  FormGroupDirective, NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators
} from "@angular/forms";
import {F} from "@angular/cdk/keycodes";
import {ErrorStateMatcher} from "@angular/material/core";

@Component({
  selector: 'reset-password',
  templateUrl: 'reset-password.component.html',
  styleUrls: ['reset-password.component.scss']
})

export class ResetPasswordComponent implements OnInit {
  constructor(private resetPasswordService: ResetPasswordService) {
  }

  checkPasswords: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
    let pass = group.get('password')?.value;
    let confirmPass = group.get('confirmPassword')?.value
    return pass === confirmPass ? null : {notSame: true}
  }

  showLoader: boolean = false;
  actionStarted: boolean = false;
  firstStep: boolean = true;
  firstStepError: boolean = false;
  firstStepSuccess: boolean = false;
  secondStep: boolean = false;
  secondStepError: boolean = false;
  secondStepSuccess: boolean = false;
  thirdStep: boolean = false;
  thirdStepError: boolean = false;
  thirdStepSuccess: boolean = false;
  matcher = new MyErrorStateMatcher();

  firstStepForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.email])
  });

  secondStepForm = new FormGroup({
    username: new FormControl({value: "", disabled: true}, Validators.required),
    code: new FormControl('', Validators.required)
  });

  thirdStepForm = new FormGroup({
      username: new FormControl({value: "", disabled: true}, Validators.required),
      password: new FormControl(undefined, Validators.required),
      confirmPassword: new FormControl(undefined, Validators.required)
    }, {
      validators: this.checkPasswords
    }
  )

  ngOnInit() {
  }

  onFirstStep = () => {
    this.showLoader = true;
    this.actionStarted = true;
    this.firstStepError = false;
    if (!this.formValid(this.firstStepForm)) {
      return;
    }
    this.resetPasswordService.firstStep(this.firstStepForm.get('username')?.value!).subscribe({
      next: (data) => {
        this.actionStarted = false;
        this.firstStep = false;
        this.secondStep = true;
        this.showLoader = false;
        this.firstStepError = false;
        this.firstStepSuccess = true;
        this.secondStepForm.get('username')?.setValue(data.username);
      },
      error: (error) => {
        console.log(error);
        this.firstStepError = true;
        this.actionStarted = false;
        this.showLoader = false;
      }
    })
  }

  onSecondStep = () => {
    this.showLoader = true;
    this.actionStarted = true;
    this.secondStepError = false;
    if (!this.formValid(this.secondStepForm)) {
      return;
    }
    this.resetPasswordService.secondStep(this.secondStepForm.get('username')?.value!, this.secondStepForm.get('code')?.value!).subscribe({
      next: (data) => {
          this.thirdStepForm.get('username')?.setValue(data.username);
          this.firstStepSuccess = false;
          this.secondStepSuccess = true;
          this.actionStarted = false;
          this.showLoader = false;
          this.secondStep = false;
          this.thirdStep = true;
      },
      error: () => {
        this.actionStarted = false;
        this.showLoader = false;
        this.secondStepError = true;
        this.firstStepSuccess = false;
        this.secondStepSuccess = false;
      }
    })
  }

  sendNewCode = () => {
    this.showLoader = true;
    this.actionStarted = true;
    this.secondStepError = false;
    this.firstStepSuccess = false;
    this.resetPasswordService.firstStep(this.secondStepForm.get('username')?.value!).subscribe({
      next: (data) => {
        this.thirdStepForm.get('username')?.setValue(data.username);
        this.actionStarted = false;
        this.showLoader = false;
        this.firstStepSuccess = true;
      },
      error: () => {
        this.actionStarted = false;
        this.showLoader = false;
        this.secondStepError = true;
        this.firstStepSuccess = false;
      }
    });
  }

  onThirdStep = () => {
    this.showLoader = true;
    this.actionStarted = true;
    this.secondStepError = false;
    if (!this.formValid(this.thirdStepForm)) {
      return;
    }
    this.resetPasswordService.thirdStep(this.thirdStepForm.get('username')?.value!, this.thirdStepForm.get('password')?.value!).subscribe({
      next: () => {
        this.thirdStepError = false;
        this.thirdStepSuccess = true;
        this.actionStarted = false;
        this.showLoader = false;
        this.secondStepSuccess = false;
        this.thirdStep = false;
      },
      error: () => {
        this.thirdStepError = true;
        this.actionStarted = false;
        this.showLoader = false;
        this.thirdStepSuccess = false;
        this.secondStepSuccess = false;
      }
    })
  }

  formValid = (fg: FormGroup) => {
    if (fg.invalid) {
      Object.keys(fg.controls)
        .forEach(value => fg.get(value)?.markAsTouched());
      this.showLoader = false;
      this.actionStarted = false;
      return false;
    }
    return true;
  }

  restartPasswordRecovery = () => {
    this.firstStep = true;
    this.secondStep = false;
    this.thirdStep = false;
    this.firstStepForm.get('username')?.setValue("");
    this.secondStepForm.get('username')?.setValue("");
    this.secondStepForm.get('code')?.setValue("");
    this.thirdStepForm.get('username')?.setValue("");
    this.thirdStepForm.get('password')?.setValue(undefined);
    this.thirdStepForm.get('confirmPassword')?.setValue(undefined);
    this.firstStepSuccess = false;
    this.secondStepSuccess = false;
    this.thirdStepSuccess = false;
    this.firstStepError = false;
    this.secondStepError = false;
    this.thirdStepError = false;
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control?.invalid && control?.parent?.dirty);
    const invalidParent = !!(control?.parent?.invalid && control?.parent?.dirty);

    return invalidCtrl || invalidParent;
  }
}
