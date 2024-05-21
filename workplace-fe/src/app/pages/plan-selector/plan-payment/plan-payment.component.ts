import {Component, OnInit} from '@angular/core';
import {PlanService} from "../plan.service";
import {MyPlan, Plan} from "../plan";
import {Router} from "@angular/router";

@Component({
  selector: 'plan-payment',
  templateUrl: 'plan-payment.component.html',
  styleUrls: ['plan-payment.component.scss']
})

export class PlanPaymentComponent implements OnInit {
  constructor(private planService: PlanService, private router: Router) {
  }

  myPlan: MyPlan = new MyPlan();
  ngOnInit() {
    this.planService.getMyPlan().subscribe({
      next: (resp) => {
        this.myPlan = resp
        if (resp.workplaceStatus === "ACTIVATED") {
          this.router.navigate(["/workplace"]);
        }
      }
    });
  }
}
