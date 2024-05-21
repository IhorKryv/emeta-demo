import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {WorkplaceService} from "../service/workplace.service";
import {PlanInfo} from "../../payment-plan/model/plan-info";
import {MacSettings} from "../../payment-plan/model/mac-settings";
import {AutocompleteData} from "../../../../common/components/autocomplete/autocomplete.component";
import {ActivatedRoute, Router} from "@angular/router";
import {WorkplaceDetails} from "../model/workplace-details";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {NotificationType} from "../../../../common/components/notification/notification-type";

@Component({
  selector: 'workplace-page',
  templateUrl: './workplace-page.component.html',
  styleUrls: ['./workplace-page.component.scss']
})
export class WorkplacePageComponent implements OnInit {

  plansAutocomplete: AutocompleteData<PlanInfo> | undefined;
  macSettings: MacSettings | undefined;
  workplace: WorkplaceDetails = new WorkplaceDetails();
  workspaceDataForm: FormGroup | undefined;
  workspacePlanForm: FormGroup | undefined;
  workspaceStatusForm: FormGroup | undefined;

  editMode: boolean = false;
  editData: boolean = true;
  editPlan: boolean = true;
  editStatus: boolean = true;
  showLoader: boolean = false;
  actionStarted: boolean = false;

  constructor(private workplaceService: WorkplaceService,
              private route: ActivatedRoute,
              private router: Router,
              private notificationService: NotificationService) {

  }

  onPlanChanged(plan: PlanInfo): void {
    if (plan.id) {
      this.workplaceService.getPlan(plan.id).subscribe(planDetails => this.macSettings = planDetails.macSettings)
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.workplaceService.getWorkspace(params['id']).subscribe(value => {
          this.initForm(value);
          if (value.plan) {
            this.onPlanChanged(value.plan);
            this.editMode = true;
            this.editData = false;
            this.editPlan = false;
            this.editStatus = false;
          }
        });
      } else {
        this.initForm(new WorkplaceDetails());
      }
    });
  }

  initForm(workspaceDetails: WorkplaceDetails) {
    this.workplace = workspaceDetails;
    this.workspaceDataForm = new FormGroup({
      firstName: new FormControl(workspaceDetails.firstName, Validators.required),
      lastName: new FormControl(workspaceDetails.lastName, Validators.required),
      email: new FormControl(workspaceDetails.email, Validators.required),
      phone: new FormControl(workspaceDetails.phone)
    });
    this.workspacePlanForm = new FormGroup({
      plan: new FormControl(workspaceDetails.plan, Validators.required)
    });
    this.workspaceStatusForm = new FormGroup({
      status: new FormControl(workspaceDetails.status, Validators.required),
      createPayment: new FormControl()
    });
    this.plansAutocomplete = new AutocompleteData<PlanInfo>(
      (<FormControl>this.workspacePlanForm.controls['plan']),
      item => item.name ? item.name : '',
      item => this.onPlanChanged(item),
      (searchText, size) => this.workplaceService.getPlans({searchText: searchText, size: size})
    )
  }

  createPaymentPlan() {
    if (!this.isDataFormValid() || !this.isPlanFormValid() || !this.isStatusFormValid()) {
      return;
    }
    this.showLoader = true;
    this.actionStarted = true
    this.fillWorkplaceDataFromForm();
    this.fillWorkplacePlanFromForm();
    this.fillWorkplaceStatusFromForm();

    this.workplaceService.createWorkplace(this.workplace).subscribe({
        next: value => {
          this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@workplace-create-success:A new workplace has been created`);
          this.router.navigate([value.id], {relativeTo: this.route.parent});
          this.showLoader = false;
          this.actionStarted = false
        },
        error: err => {
          this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't create`, err.error.error)
          this.showLoader = false;
          this.actionStarted = false
        }
      }
    )
  }

  updateWorkplaceData() {
    if (!this.isDataFormValid()) {
      return;
    }
    this.fillWorkplaceDataFromForm();
    if (this.workplace.id) {
      this.workplaceService.updateWorkplaceData(this.workplace.id, this.workplace).subscribe({
        next: value => {
          this.workplace = value;
          this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@workplace-update-success:The workplace has been updated`);
        },
        error: err => {
          this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`, err.error.error)
        }
      })
    }
  }

  updateWorkplacePlan() {
    if (!this.isPlanFormValid()) {
      return;
    }
    this.fillWorkplacePlanFromForm();
    if (this.workplace.id && this.workplace.plan?.id) {
      this.workplaceService.updateWorkplacePlan(this.workplace.id, this.workplace.plan.id).subscribe({
        next: value => {
          this.workplace = value;
          this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`,  $localize`:@@workplace-plan-update-success:The workplace plan has been updated`);
        },
        error: err => {
          this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`, err.error.error)
        }
      })
    }
  }

  updateWorkplaceStatus() {
    if (!this.isStatusFormValid()) {
      return;
    }
    this.fillWorkplaceStatusFromForm();
    if (this.workplace.id && this.workplace.status) {
      this.workplaceService.updateWorkplaceStatus(this.workplace.id, this.workplace.status).subscribe({
        next: value => {
          this.workplace = value;
          this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`,  $localize`:@@workplace-status-update-success:The workplace status has been updated`);
        },
        error: err => {
          this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`, err.error.error)
        }
      })
    }
  }

  isDataFormValid() {
    if (this.workspaceDataForm?.invalid) {
      Object.keys(this.workspaceDataForm.controls)
        .forEach(value => this.workspaceDataForm?.get(value)?.markAsTouched());
      return false;
    }
    return this.workspaceDataForm?.valid;
  }

  isPlanFormValid() {
    if (this.workspacePlanForm?.invalid) {
      Object.keys(this.workspacePlanForm.controls)
        .forEach(value => this.workspacePlanForm?.get(value)?.markAsTouched());
      return false;
    }
    return this.workspacePlanForm?.valid;
  }

  isStatusFormValid() {
    if (this.workspaceStatusForm?.invalid) {
      Object.keys(this.workspaceStatusForm.controls)
        .forEach(value => this.workspaceStatusForm?.get(value)?.markAsTouched());
      return false;
    }
    return this.workspaceStatusForm?.valid;
  }

  fillWorkplaceDataFromForm() {
    this.workplace.firstName = this.workspaceDataForm?.get('firstName')?.value!;
    this.workplace.lastName = this.workspaceDataForm?.get('lastName')?.value!;
    this.workplace.email = this.workspaceDataForm?.get('email')?.value!;
    this.workplace.phone = this.workspaceDataForm?.get('phone')?.value!;
  }

  fillWorkplacePlanFromForm() {
    this.workplace.plan = this.workspacePlanForm?.get('plan')?.value!;
  }

  fillWorkplaceStatusFromForm() {
    this.workplace.status = this.workspaceStatusForm?.get('status')?.value!;
  }
}
