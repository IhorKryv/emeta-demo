import {Component} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'payment',
  templateUrl: 'payment.component.html',
  styleUrls: ['payment.component.scss']
})

export class PaymentComponent implements MainPage {

  private PAGE_TITLE: string = $localize`:@@payment-page-title:Payments`;

  constructor() {
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
