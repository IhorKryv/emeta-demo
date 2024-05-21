import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {TablePage} from "../../../../common/components/page-template/table-page";
import {Session} from "../model/session";
import {SessionsService} from "../service/sessions.service";
import {AddSessionDialogComponent} from "../add-session-dialog/add-session.component";
import {AddClientDialogComponent} from "../add-client-dialog/add-client.component";
import {of} from "rxjs";
import {SessionInfoDialogComponent} from "../session-info-dialog/session-info.component";
import {DeviceDetectorService} from "ngx-device-detector";
import {AuthService} from "../../auth/service/auth.service";

@Component({
  selector: 'available-sessions',
  templateUrl: 'available-sessions.component.html',
  styleUrls: ['available-sessions-mobile.component.scss', 'available-sessions.component.scss']
})

export class AvailableSessionsComponent extends TablePage<Session> implements OnInit {
  constructor(dialog: MatDialog,
              private _snackBar: MatSnackBar,
              public confirmationService: ConfirmationService,
              private notificationService: NotificationService,
              private sessionsService: SessionsService,
              private deviceService: DeviceDetectorService,
              private authService: AuthService) {
    super(dialog);
    this.isMobile = this.deviceService.isMobile();
  }
  isMobile: boolean = false;

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Session> | undefined;

  public displayedColumns: string[] = ['index', 'name', 'startTime', 'status', 'session-actions', 'actions'];

  getMatPaginator(): MatPaginator | undefined {
    return undefined;
  }

  getMatTable(): MatTable<Session> | undefined {
    return undefined;
  }

  ngOnInit() {
    this.loadData();
  }

  addNewSession() {
    this.openDialog(new Session());
  }

  viewSession(session: Session) {
    this.openSessionInfoDialog(session);
  }

  editSession(session: Session) {
    this.openDialog(session);
  }

  addClients(session: Session) {
    this.openAddClientsDialog(session);
  }

  onSessionStart(session: Session) {
    this.confirmationService.show(
      () => this.startSession(session),
      () => {
      },
      {
        confirmationTitle: $localize`:@@session-start-confirm-title:Start the session?`,
        confirmationMessage: $localize`:@@session-start-confirm-message:Do you want to start this session?`,
        approveButton: $localize`:@@session-start-confirm-button:Start`
      });
  }

  onSessionStop(session: Session) {
    this.confirmationService.show(
      () => this.stopSession(session.id!),
      () => {
      },
      {
        confirmationTitle: $localize`:@@session-stop-confirm-title:Stop this session?`,
        confirmationMessage: $localize`:@@session-stop-confirm-message:Do you want to stop this session?`,
        approveButton: $localize`:@@session-stop-confirm-button:Stop`
      });
  }

  onSessionDelete(session: Session) {
    this.confirmationService.show(() => this.deleteSessionByStatus(session));
  }

  onSessionArchive(session: Session) {
    this.confirmationService.show(
      () => this.archiveSession(session.id!),
      () => {
      },
      {
        confirmationTitle: $localize`:@@session-archive-confirm-title:Archive this session?`,
        confirmationMessage: $localize`:@@session-archive-confirm-message:Do you want to archive this session?`,
        approveButton: $localize`:@@session-archive-confirm-button:Archive`
      });
  }

  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.sessionsService.getAvailableSessions(searchText, page));
  }

  instantSessionCallback(searchText: string = "", page: number = 0): void {
    this.sessionsService.getAvailableSessions(searchText, page).subscribe();
  }

  private startSession = (session: Session) => {
    this.sessionsService.startSession(session.id!).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@start-success:Successfully started`, $localize`:@@session-start-success:The session has been started`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@session-start-error:Can't start the session`, err.error.error)
    })
    let data: {name: string, key: string, type: string, userId: string, userName: string, token: string} = {
      name: session.name || '',
      key: session.id!,
      type: "PRESENTATION_SESSION_ONLY",
      userId: session.workplaceId || '',
      userName: this.authService.userData?.fullName!,
      token: localStorage.getItem("token") || ''
    }
    this.sessionsService.startSessionInstance(session, data).subscribe({
      next: (sessionInstance) => {
        if (session.id) {
          this.sessionsService.updateSessionUrl(session.id, {
            id: session.id,
            url: sessionInstance.url
          }).subscribe({
            next: (resp) => {
              this.loadData();
              const form = document.createElement('form');
              form.method = 'POST';
              form.target = '_blank';
              form.action = sessionInstance.action;
              for (let prop in data) {
                const input = document.createElement('input');
                input.type = 'hidden';
                input.name = prop;
                // @ts-ignore
                input.value = data[prop];
                form.append(input);
              }
              document.body.appendChild(form);
              form.submit();
              form.remove();
            },
            error: (err) => console.log(err.error.error)
          })
        }
      },
      error: (err) => console.log(err.error.error)
    })
  }

  private stopSession = (id: string) => {
    this.sessionsService.stopSession(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@stop-success:Successfully stopped`, $localize`:@@session-stop-success:The session has been stopped`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@session-stop-error:Can't stop the session`, err.error.error)
    })
  }

  private deleteSessionByStatus = (session: Session) => {
    if (session.inProgress) {
      this.sessionsService.stopSession(session.id!).subscribe({
        next: () => this.deleteSession(session),
        error: (err) => console.log(err)
      })
    } else {
      this.deleteSession(session);
    }
  }

  private deleteSession = (session: Session) => {
    this.sessionsService.deleteSession(session.id!).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@session-delete-success:The session has been successfully deleted`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't Delete`, err.error.error)
    })
  }

  private archiveSession = (id: string) => {
    this.sessionsService.archiveSession(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@archive-success:Successfully archived`, $localize`:@@session-archive-success:The session has been successfully archived`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@archive-error:Can't Archive`, err.error.error)
    })
  }

  private openDialog(session: Session) {
    this.createDialog(AddSessionDialogComponent, session,
      data => this.sessionsService.createSession(data),
      (id, data) => this.sessionsService.updateSession(id, data),
      data => {
        this.loadData();
        !data.id
          ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@session-create-success:A new session has been created`)
          : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@session-update-success:The session has been updated`)
      },
      (data, err) => {
        this.loadData();
        !data.id
          ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't Create`, err.error.error)
          : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't Update`, err.error.error)
      }, this.isMobile ? '100vw' : '50vw'
    );
  }

  private openAddClientsDialog(session: Session) {
    this.createDialog(AddClientDialogComponent, session,
      data => of(data),
      (id, data) => this.sessionsService.addClientsToSessions(id, data.clients),
      data => !data.id
        ? null
        : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@session-clients-update-success:Clients of current session have been updated`),
      (data, err) => !data.id
        ? null
        : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@session-clients-update-error:Updating of clients failed`, err.error.error),
      this.isMobile ? '100vw' : '50vw'
    )
  }

  private openSessionInfoDialog(session: Session) {
    this.createDialog(SessionInfoDialogComponent, session,
      data => of(data),
      (id, data) => of(data),
      () => null,
      () => null,
      this.isMobile ? '100vw' : '50vw'
    )
  }

}
