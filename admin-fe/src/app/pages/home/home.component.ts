import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";
import {CalendarSettings} from "../../../common/components/calendar/calendar.component";

@Component({
  selector: 'home-component',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.scss']
})

export class HomeComponent implements MainPage, OnInit {
  private PAGE_TITLE: string = $localize`:@@home-page-title:Dashboard`;

  @ViewChild("cellTemplate", {static: true}) cellTemplate: TemplateRef<any> | undefined;
  calendarSettings: CalendarSettings | undefined;

  constructor() {
    // This is intentional
  }

  ngOnInit(): void {
    this.calendarSettings = {
      cellsData: [{
        data: [{
          employee: "Test",
          startTime: "10:00",
          endTime: "11:00"
        },
          {
            employee: "Test1",
            startTime: "10:00",
            endTime: "11:00"
          },
          {
            employee: "Test2",
            startTime: "10:00",
            endTime: "11:00"
          },
          {
            employee: "Test3",
            startTime: "10:00",
            endTime: "11:00"
          },
          {
            employee: "Test4",
            startTime: "10:00",
            endTime: "11:00"
          }],
        date: new Date()
      },
        {
          data: [{
            employee: "Test4",
            startTime: "10:00",
            endTime: "11:00"
          }],
          date: (new Date(new Date().getTime() - 24 * 3600 * 1000))
        }],
      cellTemplate: this.cellTemplate
    }
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }

  onEventSelect(event: any | undefined) {
    console.log(event);
  }

}
