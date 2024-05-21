import {Component, Inject} from '@angular/core';
import {Client} from "../model/client";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'add-client',
  templateUrl: 'add-client.component.html',
  styleUrls: ['add-client.component.scss']
})

export class AddClientComponent {
  constructor(public dialogRef: MatDialogRef<AddClientComponent>,
              @Inject(MAT_DIALOG_DATA) public client: Client) {
  }

  saveDisabled = !!this.client.id;

  clientForm = new FormGroup({
    firstName: new FormControl(this.client.firstName, Validators.required),
    lastName: new FormControl(this.client.lastName),
    email: new FormControl(this.client.email),
    phone: new FormControl(this.client.phone)
  });

  onSave() {
    if (this.clientForm.invalid) {
      Object.keys(this.clientForm.controls)
        .forEach(value => this.clientForm.get(value)?.markAsTouched());
      return;
    }
    if (this.clientForm.valid) {
      this.client.firstName = this.clientForm.get('firstName')?.value!;
      this.client.lastName = this.clientForm.get('lastName')?.value!;
      this.client.email = this.clientForm.get('email')?.value!;
      this.client.phone = this.clientForm.get('phone')?.value!;
      this.dialogRef.close(this.client);
    }
  }

  onClose() {
    this.dialogRef.close();
  }

  onValuesChange() {
    this.saveDisabled =
      this.client.firstName === this.clientForm.get('firstName')?.value! &&
      this.client.lastName === this.clientForm.get('lastName')?.value! &&
      this.client.email === this.clientForm.get('email')?.value! &&
      this.client.phone === this.clientForm.get('phone')?.value!
  }

}
