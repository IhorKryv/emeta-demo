import {Component} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'workplace',
  templateUrl: 'workplace.component.html'
})

export class WorkplaceComponent implements MainPage {

  private PAGE_TITLE: string = $localize`:@@workplaces-page-title:Workplaces`;

  constructor() {
    // This is intentional
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
