import {Component} from "@angular/core";
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {User} from "./model/user";
import {AuthService} from "../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'registration',
  templateUrl: 'registration.component.html',
  styleUrls: ['registration.component.scss']
})

export class RegistrationComponent {
  constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute) {
  }

  user: User = new User();
  hide: boolean = true;
  isSaveDisabled: boolean = true;
  showLoader: boolean = false;
  actionStarted: boolean = false;
  showError: boolean = false;
  errorMessage: string = "";

  userForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    phone: new FormControl(''),
  });

  onSave() {
    if (this.userForm.invalid) {
      Object.keys(this.userForm.controls)
        .forEach(value => this.userForm.get(value)?.markAsTouched());
      return;
    }
    this.showError = false;
    if (this.userForm.valid) {
      this.showLoader = true;
      this.actionStarted = true;
      this.user.username = this.userForm.get('username')?.value!;
      this.user.firstName = this.userForm.get('firstName')?.value!;
      this.user.password = this.userForm.get('password')?.value!;
      this.user.lastName = this.userForm.get('lastName')?.value!;
      this.user.phone = this.userForm.get('phone')?.value!;
      this.authService.signUp(this.user).subscribe({
        next: () => {
          this.actionStarted = false;
          this.showLoader = false;
          this.showError = false;
          this.router.navigate(["../complete"], {relativeTo: this.route});
        }, error: err => {
            this.errorMessage  = err.error.status === 400
              ? $localize`:@@registration-errorMessage400:The user with current email address already exists`
              : $localize`:@@registration-errorMessage500:Something went wrong on server. Please, try again later or contact administration for support`
          this.actionStarted = false;
          this.showLoader = false;
          this.showError = true;

        }
      })
    }
  }

  isButtonDisabled() {
    this.isSaveDisabled =
      this.userForm.get('firstName')?.value! === '' ||
      this.userForm.get('lastName')?.value! === '' ||
      this.userForm.get('username')?.value! === '' ||
      this.userForm.get('password')?.value! === ''
  }

}
