import {NgModule} from '@angular/core';

import {PlanSelectorComponent} from './plan-selector.component';
import {CommonModule} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {PlanPaymentComponent} from "./plan-payment/plan-payment.component";
import {RouterLinkWithHref, RouterModule} from "@angular/router";

@NgModule({
  imports: [CommonModule, MatIconModule, MatButtonModule, RouterModule],
  exports: [],
  declarations: [PlanSelectorComponent, PlanPaymentComponent],
  providers: [],
})
export class PlanSelectorModule {
}
