import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Session} from "../../sessions/model/session";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'calendar-item',
  templateUrl: 'calendar-item.component.html',
  styleUrls: ['calendar-item.component.scss', 'calendar-item-mobile.component.scss']
})

export class CalendarItemComponent {
  constructor(public dialogRef: MatDialogRef<CalendarItemComponent>,
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
