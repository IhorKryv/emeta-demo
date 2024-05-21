import {Component, OnInit} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'storage',
  templateUrl: 'storage.component.html'
})

export class StorageComponent implements MainPage {

  private PAGE_TITLE: string = $localize `:@@storage-page-title:Storage`;
  constructor() {
    // This is intentional
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
