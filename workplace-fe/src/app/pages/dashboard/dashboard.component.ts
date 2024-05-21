import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";
import {CalendarSettings} from "../../../common/components/calendar/calendar.component";
import {SessionsService} from "../sessions/service/sessions.service";
import {SessionCalendarItem} from "../sessions/model/session-calendar-item";
import {Session, SessionStatus} from "../sessions/model/session";
import {MatDialog} from "@angular/material/dialog";
import {CalendarItemComponent} from "./calendar-item-dialog/calendar-item.component";
import {DeviceDetectorService} from "ngx-device-detector";


@Component({
  selector: 'dashboard-component',
  templateUrl: 'dashboard.component.html',
  styleUrls: ['dashboard.component.scss']
})

export class DashboardComponent implements MainPage, OnInit {
  private PAGE_TITLE: string = $localize`:@@dashboard-page-title:Dashboard`;

  @ViewChild("cellTemplate", {static: true}) cellTemplate: TemplateRef<any> | undefined;
  calendarSettings: CalendarSettings | undefined;

  constructor(private sessionService: SessionsService,
              private dialog: MatDialog,
              private deviceService: DeviceDetectorService) {
    this.isMobile = this.deviceService.isMobile();
  }

  isMobile: boolean = false;
  sessions: SessionCalendarItem[] = [];

  getTitle(): string {
    return this.PAGE_TITLE;
  }

  ngOnInit() {
    this.sessionService.getAllSessionsForCalendar().subscribe({
      next: items => {
        this.sessions = [...items];
        this.sessions.forEach(s => {
          if (new Date(s.date!).getUTCDate() < new Date().getUTCDate() && s.sessionStatus!.toString() === "NEW") {
            s.sessionStatus = SessionStatus.MISSED.toString();
          }
        })
        this.initData();
      },
      error: err => console.log(err.error.error)
    });
  }

  initData = () => {
    let filteredSessions: FilteredSessions[] = [];
    this.sessions.forEach(session => {
      let existingFiltered = filteredSessions.find(fS => fS.date === session.date);
      if (existingFiltered) {
        existingFiltered.data.push(session);
      } else {
        filteredSessions.push(new FilteredSessions([session], session.date!));
      }
    });

    let sessionsMap: Map<Date, any[]> = new Map<Date, any[]>();
    this.sessions.forEach(session => {
      if (!sessionsMap.has(session.date!)) {
        sessionsMap.set(session.date!, []);
      }
      sessionsMap.get(session.date!)!.push(session);
    });
    filteredSessions.forEach(fS => fS.date = new Date(fS.date)),
    this.calendarSettings = {
      cellsData: filteredSessions,
      cellTemplate: this.cellTemplate
    }
  }

  onEventSelect(event: any | undefined) {
    this.openSessionInfoDialog(event);
  }

  private openSessionInfoDialog(session: Session) {
    this.dialog.open(CalendarItemComponent, {
      width: this.isMobile ? '100vw' : '50vw',
      data: {...session},
    });
  }

}

export class FilteredSessions {
  data: any[] = [];
  date: Date

  constructor(sessions: any[], date: Date) {
    this.data = sessions;
    this.date = date;
  }
}
