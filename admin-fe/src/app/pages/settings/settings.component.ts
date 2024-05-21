import {Component} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'settings',
  templateUrl: 'settings.component.html'
})

export class SettingsComponent implements MainPage {
  private PAGE_TITLE: string = $localize `:@@setting-page-title:Settings`;

  constructor() {
    // This is intentional
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
