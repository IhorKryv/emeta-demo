import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTable} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {AddPlanDialogComponent} from "../add-plan-dialog/add-plan-dialog.component";
import {Plan} from "../model/plan";
import {TablePage} from "../../../../common/components/page-template/table-page";
import {MatPaginator} from "@angular/material/paginator";
import {PaymentPlanService} from "../payment-plan.service";
import {PlanDetails} from "../model/plan-details";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {NotificationType} from "../../../../common/components/notification/notification-type";

@Component({
  selector: 'plans',
  templateUrl: 'plans.component.html',
  styleUrls: ['plans.component.scss']
})

export class PlansComponent extends TablePage<Plan> implements OnInit {

  constructor(dialog: MatDialog,
              public confirmation: ConfirmationService,
              private paymentPlanService: PaymentPlanService,
              private notificationService: NotificationService) {
    super(dialog);
  }

  public displayedColumns: string[] = ['index', 'name', 'description', 'price', 'duration', 'status', 'default', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Plan> | undefined;

  addNewPlan() {
    this.openDialog(new PlanDetails());
  }

  editPlan(plan: Plan) {
    if (plan.id) {
      this.paymentPlanService.getPlan(plan.id).subscribe(value => {
        this.openDialog(value);
      });
    }
  }

  makePlanDefault(plan: Plan) {
    this.confirmation.show(() => {
      this.paymentPlanService.makePlanDefault(plan.id!).subscribe({
        next: (plan) => {
          this.notificationService.showNotification(
            NotificationType.SUCCESS,
            $localize`:@@update-success:Successfully updated`,
            $localize`:@@plan-default-success:Selected plan has been set as DEFAULT`
          );
          this.loadData();
        },
        error: (err) => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't delete`, err.error)
      })
    })
  }

  deletePlan(plan: Plan) {
    this.confirmation.show(() => {
      this.removeRow(plan,
        id => this.paymentPlanService.deletePlan(id),
        () => this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@plan-delete-success:Selected plan has been deleted`),
        (data, error) => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't delete`, error));
    })
  }

  ngOnInit() {
    this.loadData();
  }

  getMatPaginator(): MatPaginator | undefined {
    return this.paginator;
  }

  getMatTable(): MatTable<Plan> | undefined {
    return this.table;
  }

  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.paymentPlanService.getPlans(searchText, page))
  }

  private openDialog(plan: PlanDetails) {
    console.log(plan);
    this.createDialog(AddPlanDialogComponent, plan,
      data => this.paymentPlanService.addPlan(data),
      (id, data) => this.paymentPlanService.updatePlan(id, data),
      data => data.id
        ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@plan-update-success:The plan has been updated`)
        : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@plan-create-success:The plan has been created`),
      data => data.id
        ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`)
        : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't create`));
  }
}
