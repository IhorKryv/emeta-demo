import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Board} from "../model/board";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'add-board-image',
  templateUrl: 'add-board-image.component.html',
  styleUrls: ['add-board-image.component.scss']
})

export class AddBoardImageDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddBoardImageDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public board: Board) {
  }

  saveDisabled = true;
  previewUrl: string | ArrayBuffer | null = "";

  onFileSelected(event: any) {
    this.board.file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = () => this.previewUrl = reader.result as string;

    reader.readAsDataURL(event.target.files[0]);
    this.saveDisabled = false;

  }

  removeImage() {
    this.board.image = undefined;
    this.previewUrl = "";
    this.saveDisabled = true;
  }

  onClose() {
    this.dialogRef.close();
  }

  onSave() {
    if (this.board.file) {
      this.dialogRef.close(this.board);
    }
  }

}
