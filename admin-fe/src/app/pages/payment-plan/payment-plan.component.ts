import {Component} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";

@Component({
  selector: 'payment-plan',
  templateUrl: 'payment-plan.component.html',
  styleUrls: ['payment-plan.component.scss']
})

export class PaymentPlanComponent implements MainPage {
  private PAGE_TITLE: string = $localize `:@@payment-plan-page-title:Payment plans`;
  constructor() {
    // This is intentional
  }

  getTitle(): string {
    return this.PAGE_TITLE;
  }
}
