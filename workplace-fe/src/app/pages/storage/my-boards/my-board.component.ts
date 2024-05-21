import {Component, OnInit, ViewChild} from '@angular/core';
import {TablePage} from "../../../../common/components/page-template/table-page";
import {Board} from "../model/board";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {Deck} from "../model/deck";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {BoardService} from "../service/board.service";
import {AddBoardDialogComponent} from "../add-board-dialog/add-board.component";
import {AddBoardImageDialogComponent} from "../add-board-image-dialog/add-board-image.component";
import {of} from "rxjs";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'board',
  templateUrl: 'my-board.component.html',
  styleUrls: ['my-board.component.html', 'my-board-mobile.component.scss']
})

export class MyBoardComponent extends TablePage<Board> implements OnInit {
  constructor(dialog: MatDialog,
              imageDialog: MatDialog,
              private _snackBar: MatSnackBar,
              public confirmationService: ConfirmationService,
              private notificationService: NotificationService,
              private boardService: BoardService,
              private deviceService: DeviceDetectorService) {
    super(dialog);
    this.isMobile = this.deviceService.isMobile();
  }

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Deck> | undefined;

  public displayedColumns: string[] = ['index', 'name', 'description', 'image', 'actions'];

  getMatPaginator(): MatPaginator | undefined {
    return undefined;
  }

  getMatTable(): MatTable<Board> | undefined {
    return undefined;
  }

  isMobile: boolean = false;

  ngOnInit() {
    this.loadData();
  }

  addNewBoard() {
    this.openDialog(new Board());
  }

  updateImage(board: Board) {
    this.openImageDialog(board);
  }

  editBoard(board: Board) {
    this.openDialog(board);
  }

  onBoardDelete(board: Board) {
    this.confirmationService.show(() => this.deleteBoard(board.id!));
  }

  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.boardService.getAllBoards(searchText, page));
  }

  private deleteBoard = (id: string) => {
    this.boardService.deleteBoard(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@myBoard-delete-success:The board and its background image have been successfully deleted`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't Delete`, err.error.error)
    })
  }

  private openDialog(board: Board) {
    this.createDialog(AddBoardDialogComponent, board,
      data => this.boardService.createBoard(data),
      (id, data) => this.boardService.updateBoard(id, data),
      data => {
        this.loadData();
        !data.id
          ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@myBoard-create-success:A new board has been added to app`)
          : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@myBoard-update-success:The board has been updated`)
      },
      (data, err) => {
        this.loadData();
        !data.id
          ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't Create`, err.error.error)
          : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't Update`, err.error.error)
      },
      this.isMobile ? '100vw' : '40vw'
    );
  }

  private openImageDialog(board: Board) {
    this.createDialog(AddBoardImageDialogComponent, board,
      (data) => of(data),
      (id, data) => {
        const formData = new FormData();
        formData.append("file", data.file!);
        return this.boardService.uploadBoardBackgroundImage(id, formData)
      },
      data => {
        this.loadData();
        !data.id
          ? this.notificationService.showNotification(NotificationType.SUCCESS, "", "")
          : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@myBoard-image-update-success:The board background image has been updated`)
      },
      (data, err) => {
        this.loadData();
        !data.id
          ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't Create`, err.error.error)
          : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't Update`, err.error.error)
      },
      this.isMobile ? '100vw' : '40vw'
    );
  }
}
