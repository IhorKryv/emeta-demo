import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Preference} from "../model/preference";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'add-item-dialog',
  templateUrl: 'add-preference-dialog.component.html',
  styleUrls: ['add-preference-dialog.component.scss']
})

export class AddPreferenceDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddPreferenceDialogComponent, Preference>,
              @Inject(MAT_DIALOG_DATA) public preference: Preference) {

  }

  preferenceForm = new FormGroup({
    name: new FormControl(this.preference.name, Validators.required),
    key: new FormControl(this.preference.key, Validators.required),
    description: new FormControl(this.preference.description),
  });

  transformKey() {
    const obj = {
      key: this.preferenceForm.get('key')?.value!.toUpperCase().replace(" ", "_")
    };
    this.preferenceForm.patchValue(obj);
  }

  onSave() {
    if (this.preferenceForm.invalid) {
      Object.keys(this.preferenceForm.controls)
        .forEach(value => this.preferenceForm.get(value)?.markAsTouched());
      return;
    }
    if (this.preferenceForm.valid) {
      this.preference.name = this.preferenceForm.get('name')?.value!;
      this.preference.key = this.preferenceForm.get('key')?.value!;
      this.preference.description = this.preferenceForm.get('description')?.value!;
      this.dialogRef.close(this.preference);
    }
  }

  onClose() {
    this.dialogRef.close();
  }
}
