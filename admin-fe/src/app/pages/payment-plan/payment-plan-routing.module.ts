import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {PreferenceComponent} from "./preferences/preference.component";
import {PlansComponent} from "./plans/plans.component";
import {PaymentPlanService} from "./payment-plan.service";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'plans'},
  {path: 'preferences', component: PreferenceComponent},
  {path: 'plans', component: PlansComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [PaymentPlanService]
})
export class PaymentPlanRoutingModule {
}
