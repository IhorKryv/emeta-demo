import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Board} from "../model/board";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CreateBoard} from "../model/create-board";
import {BoardService} from "../service/board.service";

@Component({
  selector: 'add-board',
  templateUrl: 'add-board.component.html',
  styleUrls: ['add-board.component.scss']
})

export class AddBoardComponent {
  constructor(public dialogRef: MatDialogRef<AddBoardComponent>,
              @Inject(MAT_DIALOG_DATA) public board: Board,
              private boardService: BoardService) {
    console.log(this.board)
  }

  public boardForm = new FormGroup({
    name: new FormControl(this.board.name || '', Validators.required),
    description: new FormControl(this.board.description || '')
  });

  image: any = undefined;
  previewURL: string | undefined;
  imageError: boolean = false;

  removeImage = () => {
    this.image = undefined;
    this.previewURL = undefined;
    this.board.imageUrl = undefined;
  }

  onFileSelected(event: any) {
    this.image = event.target.files[0];
    const reader = new FileReader();
    reader.onload = () => this.previewURL = reader.result as string;
    reader.readAsDataURL(event.target.files[0]);
  }

  onClose = () => {
    this.dialogRef.close();
  }

  onSave = () => {
    if (this.boardForm.invalid) {
      Object.keys(this.boardForm.controls)
        .forEach(value => this.boardForm.get(value)?.markAsTouched());
      return;
    }
    if (!this.image) {
      this.imageError = true;
      return;
    }
    if (this.boardForm.valid) {
      const formData = new FormData();
      formData.append("name", this.boardForm.get('name')?.value!);
      formData.append("description", this.boardForm.get('description')?.value!);
      formData.append("file", this.image);
      if (this.board.id!) {
        this.boardService.updateBoard(this.board.id, formData).subscribe({
          next: () => {
            this.dialogRef.close({success: true, edit: true});
            this.imageError = false;
          },
          error: err => console.log(err.error.error)
        });
      } else {
        this.boardService.createBoard(formData).subscribe({
          next: () => {
            this.dialogRef.close({success: true, edit: false});
            this.imageError = false;
          },
          error: err => console.log(err.error.error)
        });
      }
    }
  }
}
