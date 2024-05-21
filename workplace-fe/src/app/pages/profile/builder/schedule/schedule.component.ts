import {Component, OnInit} from '@angular/core';
import {Schedule} from "./schedule";
import {ScheduleService} from "./schedule.service";
import {debounceTime, Subject} from "rxjs";

@Component({
  selector: 'schedule',
  templateUrl: 'schedule.component.html',
  styleUrls: ['schedule.component.scss']
})

export class ScheduleComponent implements OnInit {
  protected readonly Object = Object;
  private inputChangeSubject = new Subject<any>();
  constructor(private scheduleService: ScheduleService) {
    this.inputChangeSubject
      .pipe(debounceTime(500))
      .subscribe(value => {
        if (value.target === "title") {
          this.scheduleService.saveTitle(value.text || null).subscribe();
        }
        if (value.target === "short-text") {
          this.scheduleService.saveShortText(value.text || null).subscribe();
        }
        if (value.target === "item") {
          this.scheduleService.saveScheduleItem(value.key, value.text || null).subscribe();
        }
      });
  }

  schedule: Schedule = new Schedule();


  ngOnInit() {
    this.scheduleService.getSchedule().subscribe({
      next: (resp) => {
        this.schedule = resp;
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  onTitleChange() {
    this.inputChangeSubject.next({target: "title", text: this.schedule.title!});
  }

  onShortTextChange() {
    this.inputChangeSubject.next({target: "short-text", text: this.schedule.shortText!});
  }

  onItemChange(key: string, value: string) {
    this.inputChangeSubject.next({key: key, target: "item", text: value});
  }


  getScheduleDay(key: string): string {
    switch (key) {
      case "1":
        return "Monday";
      case "2":
        return "Tuesday";
      case "3":
        return "Wednesday";
      case "4":
        return "Thursday";
      case "5":
        return "Friday";
      case "6":
        return "Saturday";
      case "7":
        return "Sunday";
      default:
        return "";
    }
  }

}
