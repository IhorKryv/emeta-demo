import {Component} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'security',
  templateUrl: 'security.component.html'
})

export class SecurityComponent implements MainPage {
  private PAGE_TITLE: string = $localize `:@@security-page-title:Security`;
  constructor() {
    // This is intentional
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
