import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Role} from "../roles/model/role";
import {PermissionService} from "../permissions/service/permission.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'add-roles-dialog',
  templateUrl: 'add-roles.component.html',
  styleUrls: ['add-roles.components.scss']
})

export class AddRolesDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddRolesDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public role: Role,
              private permissionService: PermissionService) {

    this.permissionService.getAllAuthorities().subscribe({
      next: (permissions) => {

        this.authorities = permissions.map(p => p.name);
      },
      error: (error) => {
        console.log(error);
      }
    });

  }
  public authorities: string[] = [];
  public roleForm = new FormGroup({
    name: new FormControl(this.role.name || '', Validators.required),
    description: new FormControl(this.role.description || '', Validators.required),
    permissions: new FormControl(this.role.authorities || [], Validators.required)
  });

  onClose() {
    this.dialogRef.close();
  }

  validate() {
    if (this.roleForm.invalid) {
      this.roleForm.get('name')?.markAsTouched();
      this.roleForm.get('description')?.markAsTouched();
      this.roleForm.get('permissions')?.markAsTouched();
      return;
    }
    if (this.roleForm.valid) {
      this.role.name = this.roleForm.get('name')?.value!;
      this.role.description = this.roleForm.get('description')?.value!;
      this.role.authorities = this.roleForm.get('permissions')?.value!;
      this.dialogRef.close(this.role);
    }
  }

}

