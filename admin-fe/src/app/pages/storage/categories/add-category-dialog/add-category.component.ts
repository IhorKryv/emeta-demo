import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Role} from "../../../security/roles/model/role";
import {PermissionService} from "../../../security/permissions/service/permission.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Category} from "../model/category";

@Component({
  selector: 'add-category-dialog',
  templateUrl: 'add-category.component.html',
  styleUrls: ['add-category.component.scss']
})

export class AddCategoryDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddCategoryDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public category: Category) {
  }
  public categoryForm = new FormGroup({
    name: new FormControl(this.category.name || '', Validators.required),
    identifier: new FormControl(this.category.identifier || '', Validators.required),
    description: new FormControl(this.category.description || ''),
  });

  onClose() {
    this.dialogRef.close();
  }

  onSave() {
    if (this.categoryForm.invalid) {
      Object.keys(this.categoryForm.controls)
        .forEach(value => this.categoryForm.get(value)?.markAsTouched());
      return;
    }
    if (this.categoryForm.valid) {
      this.category.name = this.categoryForm.get('name')?.value!;
      this.category.identifier = this.categoryForm.get('identifier')?.value!;
      this.category.description = this.categoryForm.get('description')?.value!;
      this.dialogRef.close(this.category);
    }
  }
}
