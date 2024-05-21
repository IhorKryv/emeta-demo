import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {SessionsService} from "../service/sessions.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {Session} from "../model/session";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {AddSessionDialogComponent} from "../add-session-dialog/add-session.component";
import {TablePage} from "../../../../common/components/page-template/table-page";

@Component({
  selector: 'archived-sessions',
  templateUrl: 'archived-sessions.component.html'
})

export class ArchivedSessionsComponent extends TablePage<Session> implements OnInit {
  constructor(dialog: MatDialog,
              private _snackBar: MatSnackBar,
              private notificationService: NotificationService,
              private sessionsService: SessionsService) {
    super(dialog);
  }

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Session> | undefined;

  public displayedColumns: string[] = ['index', 'name', 'createdAt', 'updatedAt', 'startTime', 'endTime', 'duration', 'actions'];

  getMatPaginator(): MatPaginator | undefined {
    return undefined;
  }

  getMatTable(): MatTable<Session> | undefined {
    return undefined;
  }

  ngOnInit() {
    this.loadData();
  }


  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.sessionsService.getArchivedSessions(searchText, page));
  }

}
