import {Component, OnInit} from '@angular/core';
import {BoardService} from "./service/board.service";
import {Board} from "./model/board";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AddBoardComponent} from "./add-board-dialog/add-board.component";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {Deck} from "../decks/model/deck";

@Component({
  selector: 'board',
  templateUrl: 'board.component.html',
  styleUrls: ['board.component.scss']
})

export class BoardComponent implements OnInit {
  constructor(dialog: MatDialog,
              private _snackBar: MatSnackBar,
              private boardService: BoardService,
              private confirmationService: ConfirmationService,
              private notificationService: NotificationService) {
    this.dialog = dialog;
  }
  dialog: MatDialog;

  boards: Board[] = [];
  showLoader: boolean = true;

  ngOnInit() {
    this.getAllBoards();
  }

  manageBoard = (board?: Board) => {
    const dialogRef = this.dialog.open(AddBoardComponent, {
      width: "40vw",
      data: board ? board : {}
    });
    dialogRef.afterClosed().subscribe((data) => {
      if (data.success) {
        let title = data.edit ? $localize`:@@update-success:Successfully updated` : $localize`:@@create-success:Successfully created`;
        let text = data.edit ? $localize`:@@board-update-success:The board has been updated` : $localize`:@@board-create-success:The board has been created`;
        this.notificationService.showNotification(NotificationType.SUCCESS, title, text);
        this.getAllBoards();
      }
    })
  }

  onBoardDelete(board: Board) {
    this.confirmationService.show(() => this.deleteBoard(board.id!));
  }

  private deleteBoard = (id: string) => {
    this.boardService.deleteBoard(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@board-delete-success:The board has been deleted`)
        this.getAllBoards();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't delete`, err.error.error)
    })
  }

  getAllBoards = () => {
    this.boardService.getAllBoards().subscribe({
      next: data => {
        this.boards = [...data];
        this.showLoader = false;
      },
      error: err => console.log(err.error.error)
    })
  }
}
