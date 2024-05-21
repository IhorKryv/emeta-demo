import {Component, OnInit} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'clients',
  templateUrl: 'clients.component.html',
  styleUrls: ['clients.component.scss']
})

export class ClientsComponent implements MainPage {

  private PAGE_TITLE: string = $localize `:@@clients-page-title:Clients`;
  constructor() {
    // This is intentional
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
