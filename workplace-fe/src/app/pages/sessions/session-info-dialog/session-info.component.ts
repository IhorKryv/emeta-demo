import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Session, SessionStatus} from "../model/session";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'session-info',
  templateUrl: 'session-info.component.html',
  styleUrls: ['session-info.component.scss', 'session-info-mobile.components.scss']
})

export class SessionInfoDialogComponent {
  constructor(public dialogRef: MatDialogRef<SessionInfoDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public session: Session,
              private deviceService: DeviceDetectorService) {
    this.currentSessionStatus = session.sessionStatus!.toString();
    this.isMobile = this.deviceService.isMobile();
  }

  isMobile: boolean = false;
  currentSessionStatus: string | undefined;

  onClose() {
    this.dialogRef.close();
  }

  hideAdditionalIcon(value: string) {
    return value === '';
  }

}
