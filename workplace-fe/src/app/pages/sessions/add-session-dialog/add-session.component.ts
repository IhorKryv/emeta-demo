import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Session} from "../model/session";

@Component({
  selector: 'add-session',
  templateUrl: 'add-session.component.html',
  styleUrls: ['add-session.component.scss']
})

export class AddSessionDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddSessionDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public session: Session) {
    console.log(session);
  }

  saveDisabled = !!this.session.id;

  public sessionForm = new FormGroup({
    name: new FormControl(this.session.name || '', Validators.required),
    description: new FormControl(this.session.description || ''),
    startTime: new FormControl(this.session.startTime || null, Validators.required),
    sessionDuration: new FormControl(this.session.sessionDuration || null, Validators.required)
  });

  onClose() {
    this.dialogRef.close();
  }

  onSave() {
    if (this.sessionForm.invalid) {
      Object.keys(this.sessionForm.controls)
        .forEach(value => this.sessionForm.get(value)?.markAsTouched());
      return;
    }
    if (this.sessionForm.valid) {
      this.session.name = this.sessionForm.get('name')?.value!;
      this.session.description = this.sessionForm.get('description')?.value!;
      this.session.startTime = this.sessionForm.get('startTime')?.value!;
      this.session.sessionDuration = this.sessionForm.get('sessionDuration')?.value!;
      this.dialogRef.close(this.session);
    }
  }

  onValuesChange() {
    this.saveDisabled =
      this.session.name === this.sessionForm.get('name')?.value! &&
      this.session.description === this.sessionForm.get('description')?.value! &&
      this.session.startTime === this.sessionForm.get('startTime')?.value! &&
      this.session.sessionDuration === this.sessionForm.get('sessionDuration')?.value!
  }
}
