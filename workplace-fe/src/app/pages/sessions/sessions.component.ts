import {Component} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'sessions',
  templateUrl: 'sessions.component.html'
})

export class SessionsComponent implements MainPage {

  private PAGE_TITLE: string = $localize `:@@sessions-page-title:Sessions`;
  constructor() {
    // This is intentional
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
