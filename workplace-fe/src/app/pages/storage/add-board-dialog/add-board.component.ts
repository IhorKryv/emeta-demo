import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Board} from "../model/board";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'add-board',
  templateUrl: 'add-board.component.html',
  styleUrls: ['add-board.component.scss']
})

export class AddBoardDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddBoardDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public board: Board) {
  }

  saveDisabled = !!this.board.id;

  public boardForm = new FormGroup({
    name: new FormControl(this.board.name || '', Validators.required),
    description: new FormControl(this.board.description || '')
  });

  onClose() {
    this.dialogRef.close();
  }

  onSave() {
    if (this.boardForm.invalid) {
      Object.keys(this.boardForm.controls)
        .forEach(value => this.boardForm.get(value)?.markAsTouched());
      return;
    }
    if (this.boardForm.valid) {
      this.board.name = this.boardForm.get('name')?.value!;
      this.board.description = this.boardForm.get('description')?.value!;
      this.dialogRef.close(this.board);
    }
  }

  onValuesChange() {
    this.saveDisabled =
      this.board.name === this.boardForm.get('name')?.value! &&
      this.board.description === this.boardForm.get('description')?.value!
  }
}
