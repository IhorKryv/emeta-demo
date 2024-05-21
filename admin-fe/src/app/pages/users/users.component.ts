import {Component} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'users',
  templateUrl: 'users.component.html'
})

export class UsersComponent implements MainPage {
  private PAGE_TITLE: string = $localize `:@@users-page-title:Users`;
  constructor() {
    // This is intentional
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
