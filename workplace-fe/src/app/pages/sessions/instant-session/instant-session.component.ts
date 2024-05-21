import {Component, Input, OnInit} from '@angular/core';
import {SessionsService} from "../service/sessions.service";
import {Session} from "../model/session";
import {AuthService} from "../../auth/service/auth.service";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {NotificationService} from "../../../../common/components/notification/notification.service";

@Component({
  selector: 'instant-session',
  templateUrl: 'instant-session.component.html',
  styleUrls: ['instant-session.component.scss']
})

export class InstantSessionComponent {

  @Input("callback") callback: Function | undefined;

  constructor(private sessionsService: SessionsService, private authService: AuthService, private notificationService: NotificationService) {
  }

  createInstantSession = () => {
    let session: Session = new Session();
    session.name = "Instant Session [" + new Date().getTime() + "]";
    session.description = "This session has been created as instant session";
    session.startTime = new Date();
    session.sessionDuration = 60;
    session.instant = true;
    console.log(session);
    this.sessionsService.createSession(session).subscribe({
      next: (session) => {
        this.sessionsService.startSession(session.id!).subscribe({
          next: () => {
            this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@start-success:Successfully started`, $localize`:@@session-start-success:The session has been started`)
            if (this.callback) {
              this.callback("", 0);
            }
          }
        });
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
    })
  }
}
