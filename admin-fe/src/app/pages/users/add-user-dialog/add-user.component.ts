import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {User} from "../model/user";
import {RoleService} from "../../security/roles/service/role.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'add-user',
  templateUrl: 'add-user.component.html',
  styleUrls: ['add-user.component.scss']
})

export class AddUserComponent {
  constructor(public dialogRef: MatDialogRef<AddUserComponent>,
              private roleService: RoleService,
              @Inject(MAT_DIALOG_DATA) public user: User) {
    this.roleService.getAllRoles().subscribe(resp => {
      this.roleNames = resp.items.map(r => r.name!);
    });
  }

  saveDisabled = !!this.user.id;
  roleNames: string[] = [];

  userForm = new FormGroup({
    username: new FormControl({
      value: this.user.username,
      disabled: !!this.user.id
    }, [Validators.required, Validators.email]),
    firstName: new FormControl(this.user.firstName, Validators.required),
    lastName: new FormControl(this.user.lastName, Validators.required),
    roles: new FormControl(this.user.roles, Validators.required)
  });

  onSave() {
    if (this.userForm.invalid) {
      Object.keys(this.userForm.controls)
        .forEach(value => this.userForm.get(value)?.markAsTouched());
      return;
    }
    if (this.userForm.valid) {
      this.user.username = this.userForm.get('username')?.value!;
      this.user.firstName = this.userForm.get('firstName')?.value!;
      this.user.lastName = this.userForm.get('lastName')?.value!;
      this.user.roles = this.userForm.get('roles')?.value!;
      this.dialogRef.close(this.user);
    }
  }

  onClose() {
    this.dialogRef.close();
  }

  onValuesChange() {
    this.saveDisabled =
      this.user.firstName === this.userForm.get('firstName')?.value! &&
      this.user.lastName === this.userForm.get('lastName')?.value! &&
      this.arrayEquals(this.user.roles, this.userForm.get('roles')?.value!)
  }

  private arrayEquals = (a: any[], b: any[]) => {
    return Array.isArray(a) &&
      Array.isArray(b) &&
      a.length === b.length &&
      a.every(v => b.includes(v));
  }
}
