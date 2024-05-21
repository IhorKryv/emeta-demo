import {Component, OnInit} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";
import {UserService} from "../auth/registration/service/user.service";
import {User} from "../auth/registration/model/user";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UpdatePasswordRequest} from "../auth/model/updatePassword";
import {PlanService} from "../plan-selector/plan.service";
import {MyPlan} from "../plan-selector/plan";
import {Router} from "@angular/router";

@Component({
  selector: 'settings',
  templateUrl: 'settings.component.html',
  styleUrls: ['settings.component.scss']
})

export class SettingsComponent implements OnInit, MainPage {

  private PAGE_TITLE: string = $localize `:@@settings-page-title:Settings`;
  constructor(private userService: UserService, private planService: PlanService, private router: Router) {
  }

  currentUser: User = new User();
  oldData: User = new User();
  passwordsNotMatch: boolean = false;
  oldPasswordIsWrong: boolean = false;
  somethingWentWrong: boolean = false;

  updateDisable: boolean = true;

  myPlan: MyPlan = new MyPlan();

  profileForm: FormGroup | undefined;
  passwordForm: FormGroup = new FormGroup({
    oldPassword: new FormControl("", [Validators.required]),
    newPassword: new FormControl("", [Validators.required, Validators.minLength(8)]),
    confirmPassword: new FormControl("", [Validators.required, Validators.minLength(8)])
  });

  ngOnInit() {
    this.getCurrentUser();
    this.getMyPlan();
  }

  getMyPlan = () => {
    this.planService.getMyPlan().subscribe({
      next: (resp) => this.myPlan = resp
    })
  }


  getCurrentUser = () => {
    this.userService.getCurrent().subscribe({
      next: (user) => {
        this.currentUser = user;
        this.oldData = user;
        this.profileForm = new FormGroup({
          firstname: new FormControl(this.currentUser.firstName || "", Validators.required),
          lastname: new FormControl(this.currentUser.lastName || "", Validators.required),
          phone: new FormControl(this.currentUser.phone || "")
        })
        this.updateDisable = true;
      }
    })
  }

  updatePassword = () => {
    if (this.passwordForm!.invalid) {
      Object.keys(this.passwordForm!.controls)
        .forEach(value => this.passwordForm!.get(value)?.markAsTouched());
      return;
    }
    if (this.passwordForm!.get("newPassword")?.value !== this.passwordForm.get("confirmPassword")?.value) {
      this.passwordsNotMatch = true;
      this.oldPasswordIsWrong = false;
      this.somethingWentWrong = false;
      return;
    }
    if (this.profileForm!.valid) {
      let newData: UpdatePasswordRequest = new UpdatePasswordRequest();
      newData.oldPassword = this.passwordForm?.get("oldPassword")!.value!;
      newData.newPassword = this.passwordForm?.get("newPassword")!.value!;
      this.userService.updateUserPassword(this.currentUser.id!, newData).subscribe({
        next: () => {
          this.getCurrentUser();
          this.passwordsNotMatch = false;
          this.oldPasswordIsWrong = false;
          this.somethingWentWrong = false;
        },
        error: (err) => {
          this.passwordsNotMatch = false;
          if (err.error.status === 400) {
            this.oldPasswordIsWrong = true;
          } else {
            this.somethingWentWrong = true;
          }
        }
      })
    }
  }

  selectPlanRedirect = () => {
    this.router.navigate(["/plan-selector"]);
  }

  updateProfile = () => {
    if (this.profileForm!.invalid) {
      Object.keys(this.profileForm!.controls)
        .forEach(value => this.profileForm!.get(value)?.markAsTouched());
      return;
    }
    if (this.profileForm!.valid) {
      let newData: User = new User();
      newData.firstName = this.profileForm?.get("firstname")!.value!;
      newData.lastName = this.profileForm?.get("lastname")!.value!;
      newData.phone = this.profileForm?.get("phone")!.value!;
      this.userService.updateUserProfile(this.currentUser.id!, newData).subscribe({
        next: () => this.getCurrentUser()
      })
    }
  }

  onValuesChange = () => {
    this.updateDisable = this.profileForm?.get("firstname")!.value === this.oldData.firstName &&
      this.profileForm?.get("lastname")!.value === this.oldData.lastName &&
      this.profileForm?.get("phone")!.value === this.oldData.phone;
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
