import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {BoardService} from "../service/board.service";
import {Board} from "../model/board";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'edit-admin-board',
  templateUrl: 'edit-admin-board.component.html',
  styleUrls: ['edit-admin-board.component.scss']
})

export class EditAdminBoardComponent {
  constructor(public dialogRef: MatDialogRef<EditAdminBoardComponent>,
              @Inject(MAT_DIALOG_DATA) public board: Board,
              private boardService: BoardService) {
  }

  boardCustomForm: FormGroup = new FormGroup<any>({
    customName: new FormControl(this.board.customName || ''),
    customDescription: new FormControl(this.board.customDescription || '')
  })

  onClose() {
    this.dialogRef.close();
  }

  onSave() {
    const customName = this.boardCustomForm.get('customName')?.value!;
    const customDescription = this.boardCustomForm.get('customDescription')?.value!;
    this.boardService.updateAdminBoard(this.board.id!, customName, customDescription).subscribe({
      next: () => this.dialogRef.close(true),
      error: err => console.log(err.error.error)
    });
  }
}
