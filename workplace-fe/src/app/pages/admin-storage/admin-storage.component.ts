import {Component, OnInit} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'admin-storage',
  templateUrl: 'admin-storage.component.html'
})

export class AdminStorageComponent implements MainPage {
  private PAGE_TITLE: string = $localize `:@@admin-page-title:EMeta Storage`;
  constructor() {
    // This is intentional
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
