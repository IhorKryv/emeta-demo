import {Component, OnInit} from '@angular/core';
import {PlanService} from "./plan.service";
import {Plan, PlanDuration} from "./plan";
import {Router} from "@angular/router";
import {ConfirmationService} from "../../../common/components/confirmation/confirmation.service";

@Component({
  selector: 'plan-selector',
  templateUrl: 'plan-selector.component.html',
  styleUrls: ['plan-selector.component.scss']
})

export class PlanSelectorComponent implements OnInit {
  constructor(private planService: PlanService, private router: Router, private confirmationService: ConfirmationService) {
  }

  plans: Plan[] = [];

  ngOnInit() {
    this.planService.getAllPlans().subscribe({
      next: (data) => {
        const filteredPlans = data.filter(p => !p.defaultPlan && p.name !== "TEST PLAN" && p.name !== "SUPER PLAN");
        this.plans = [...filteredPlans]
      }
    })
  }

  onPlanSelect = (plan: Plan) => {
    this.confirmationService.show(() => this.selectPlan(plan), () => {
    }, {confirmationTitle: "Plan Selection", confirmationMessage: "Select current plan?"});
  }

  selectPlan = (plan: Plan) => {
    this.planService.subscribe(plan).subscribe({
      next: () => {
        this.router.navigate(["/workplace"])
      }
    })
  }


  getDuration = (value: string) => {
    switch (value) {
      case "DAY":
        return "day";
      case "MONTH":
        return "month";
      case "SIX_MONTHS":
        return "six months";
      case "THREE_MONTHS":
        return "three months";
      case "WEEK":
        return "week";
      case "YEAR":
        return "year";
      default:
        return "";
    }
  }
}
