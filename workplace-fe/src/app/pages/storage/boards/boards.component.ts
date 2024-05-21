import {Component, OnInit} from '@angular/core';
import {Board} from "../model/board";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {BoardService} from "../service/board.service";
import {EditAdminBoardComponent} from "../edit-admin-board-dialog/edit-admin-board.component";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {
  AdminBoardPreviewComponent
} from "../../../../common/components/admin-board-preview/admin-board-preview.component";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'boards',
  templateUrl: 'boards.component.html',
  styleUrls: ['boards.component.scss']
})

export class BoardsComponent implements OnInit {
  constructor(dialog: MatDialog,
              private _snackBar: MatSnackBar,
              private boardService: BoardService,
              private notificationService: NotificationService,
              private deviceService: DeviceDetectorService) {
    this.dialog = dialog;
    this.isMobile = this.deviceService.isMobile();
  }

  isMobile: boolean = false;

  dialog: MatDialog;

  boards: Board[] = [];
  showLoader: boolean = true;

  ngOnInit() {
    this.loadData();
  }

  private loadData() {
    this.boardService.getAllSelectedAdminBoards().subscribe({
      next: (data) => {
        this.boards = [...data];
        this.showLoader = false;
      },
      error: err => console.log(err.error.error)
    })
  }

  openBoard = (board: Board) => {
    this.dialog.open(AdminBoardPreviewComponent, {
      width: this.isMobile ? "100vw" : "65vw",
      data: {
        board: {...board},
        showAddButton: false
      },
    });
  }

  public editBoard(board: Board) {
    const dialogRef = this.dialog.open(EditAdminBoardComponent, {
      width: this.isMobile ? "100vw" : "30vw",
      data: {...board},
    });
    dialogRef.afterClosed().subscribe((res: boolean) => {
      if (res) {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@admin-board-update-success:EMeta Board has been updated`)
        this.loadData();
      }
    });
  }

}
