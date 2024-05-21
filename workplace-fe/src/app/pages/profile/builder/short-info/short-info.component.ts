import {Component, OnInit} from '@angular/core';
import {ShortInfo} from "./short-info";
import {ShortInfoService} from "./short-info.service";
import {debounceTime, Subject} from "rxjs";

@Component({
  selector: 'short-info',
  templateUrl: 'short-info.component.html',
  styleUrls: ['short-info.component.scss']
})

export class ShortInfoComponent implements OnInit {
  private inputChangeSubject = new Subject<any>();

  shortInfo: ShortInfo = new ShortInfo();
  constructor(private shortInfoService: ShortInfoService) {
    this.inputChangeSubject
      .pipe(debounceTime(500))
      .subscribe(value => {
        switch (value.target) {
          case "title":
            this.shortInfoService.saveTitle(value.text).subscribe();
            break;
          case "description":
            this.shortInfoService.saveDescription(value.text).subscribe();
            break;
          case "card-icon":
            this.shortInfoService.saveCardIcon(value.text, value.cardId).subscribe();
            break;
          case "card-title":
            this.shortInfoService.saveCardTitle(value.text, value.cardId).subscribe();
            break;
          case "card-text":
            this.shortInfoService.saveCardText(value.text, value.cardId).subscribe();
            break;
        }
      });
  }

  ngOnInit() {
    this.shortInfoService.getShortInfo().subscribe({
      next: (resp) => {
        this.shortInfo = resp;
      }
    })
  }

  onTitleChange() {
    this.inputChangeSubject.next({target: "title", text: this.shortInfo.title!});
  }

  onDescriptionChange() {
    this.inputChangeSubject.next({target: "description", text: this.shortInfo.description!});
  }

  onCardIconChange(value: string, cardId: string) {
    this.inputChangeSubject.next({target: "card-icon", text: value, cardId: cardId});
  }

  onCardTitleChange(value: string, cardId: string) {
    this.inputChangeSubject.next({target: "card-title", text: value, cardId: cardId});
  }

  onCardTextChange(value: string, cardId: string) {
    this.inputChangeSubject.next({target: "card-text", text: value, cardId: cardId});
  }
}
