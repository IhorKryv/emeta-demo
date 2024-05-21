import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClientRecord} from "../model/client-record";

@Component({
  selector: 'add-client-record',
  templateUrl: 'add-client-record.component.html',
  styleUrls: ['add-client-record-component.scss']
})

export class AddClientRecordComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<AddClientRecordComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  clientRecord: ClientRecord = new ClientRecord();

  clientRecordForm = new FormGroup({
    title: new FormControl("" || this.data?.record?.title, Validators.required),
    content: new FormControl("" || this.data?.record?.content),
    sessionDate: new FormControl("" || this.data?.record?.sessionDate)
  });
  ngOnInit() {
  }

  onSave() {
    if (this.clientRecordForm.invalid) {
      Object.keys(this.clientRecordForm.controls)
        .forEach(value => this.clientRecordForm.get(value)?.markAsTouched());
      return;
    }
    if (this.clientRecordForm.valid) {
      this.clientRecord.title = this.clientRecordForm.get('title')?.value!;
      this.clientRecord.content = this.clientRecordForm.get('content')?.value!;
      this.clientRecord.sessionDate = this.clientRecordForm.get('sessionDate')?.value!;
      if (this.data?.record?.id) {
        this.clientRecord.id = this.data.record.id!;
      }
      this.dialogRef.close(this.clientRecord);
    }
  }

  onClose() {
    this.dialogRef.close();
  }
}
