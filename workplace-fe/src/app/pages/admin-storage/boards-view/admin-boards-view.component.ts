import {Component, OnInit} from '@angular/core';
import {BoardService} from "../../storage/service/board.service";
import {Board} from "../../storage/model/board";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Deck} from "../../storage/model/deck";
import {AdminDeckPreviewComponent} from "../../../../common/components/admin-deck-preview/admin-deck-preview.component";
import {
  AdminBoardPreviewComponent
} from "../../../../common/components/admin-board-preview/admin-board-preview.component";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'admin-boards-view',
  templateUrl: 'admin-boards-view.component.html',
  styleUrls: ['admin-boards-view.component.scss']
})

export class AdminBoardsViewComponent implements OnInit {
  constructor(dialog: MatDialog,
              private _snackBar: MatSnackBar,
              private boardService: BoardService,
              private deviceService: DeviceDetectorService) {
    this.dialog = dialog;
    this.isMobile = this.deviceService.isMobile();
    this.getStats();
  }

  isMobile: boolean = false;
  dialog: MatDialog;
  boards: Board[] = []
  current: number = 0;
  limit: number = 0;
  showLoader: boolean = true;

  ngOnInit() {
    this.loadData();
  }

  loadData = () => {
    this.boardService.getAllBoardsFromAdmin().subscribe({
      next: data => {
        this.boards = [...data];
        this.showLoader = false;
      },
      error: err => console.log(err.error.error)
    })
  }

  openBoard= (board: Board) => {
    this.openInfoDialog(board);
  }

  private getStats = () => {
    this.boardService.getAdminBoardStats().subscribe({
      next: (data) => {
        this.current = data["current"];
        this.limit = data["limit"];
      },
      error: err => console.log(err.error.error)
    })
  }

  private openInfoDialog(board: Board) {
    const show = this.current < this.limit && !board.boardInCollection;
    const dialogRef = this.dialog.open(AdminBoardPreviewComponent, {
      width: this.isMobile ? "100vw" : "65vw",
      data: {
        board: {...board},
        showAddButton: show
      },
    });
    dialogRef.afterClosed().subscribe((res: boolean) => {
      if (res) {
        this.loadData();
        this.getStats();
      }
    });
  }
}
