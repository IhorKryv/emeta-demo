  import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Preference} from "../model/preference";
import {PaymentPlanService} from "../payment-plan.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PlanDetails} from "../model/plan-details";

@Component({
  selector: 'add-item-dialog',
  templateUrl: 'add-plan-dialog.component.html',
  styleUrls: ['add-plan-dialog.component.scss']
})

export class AddPlanDialogComponent {
  preferenceList: Preference[] = [];

  planForm = new FormGroup({
    enabled: new FormControl(this.plan.enabled),
    name: new FormControl(this.plan.name, Validators.required),
    description: new FormControl(this.plan.description),
    price: new FormControl(this.plan.price, Validators.required),
    duration: new FormControl(this.plan.duration, Validators.required),
    numberOfDecks: new FormControl(this.plan.macSettings.numberOfFreeDecks, Validators.required),
    numberOfBoards: new FormControl(this.plan.macSettings.numberOfFreeBoards, Validators.required),
    groupSize: new FormControl(this.plan.macSettings.maxGroupSize, Validators.required),
    personalPage: new FormControl(this.plan.macSettings.personalPage, Validators.required),
    ownCards: new FormControl(this.plan.macSettings.allowOwnCards, Validators.required),
    preferences: new FormControl(this.plan.preferences?.map(value => value.id))
  })

  constructor(public dialogRef: MatDialogRef<AddPlanDialogComponent>, private paymentPlanService: PaymentPlanService, @Inject(MAT_DIALOG_DATA) public plan: PlanDetails) {
    this.paymentPlanService.getItemsList().subscribe(value => this.preferenceList = value);
  }

  onSave() {
    console.log(this.planForm);
    if (this.planForm.invalid) {
      Object.keys(this.planForm.controls)
        .forEach(value => this.planForm.get(value)?.markAsTouched());
      return;
    }
    if (this.planForm.valid) {
      this.plan.enabled = this.planForm.get('enabled')?.value!;
      this.plan.name = this.planForm.get('name')?.value!;
      this.plan.description = this.planForm.get('description')?.value!;
      this.plan.price = this.planForm.get('price')?.value!;
      this.plan.duration = this.planForm.get('duration')?.value!;
      this.plan.macSettings.numberOfFreeDecks = this.planForm.get('numberOfDecks')?.value!;
      this.plan.macSettings.numberOfFreeBoards = this.planForm.get('numberOfBoards')?.value!;
      this.plan.macSettings.maxGroupSize = this.planForm.get('groupSize')?.value!;
      this.plan.macSettings.personalPage = this.planForm.get('personalPage')?.value!;
      this.plan.macSettings.allowOwnCards = this.planForm.get('ownCards')?.value!;
      this.plan.preferences = this.planForm.get('preferences')?.value!.map((id) => {
        return {id: id}
      });
      this.plan.defaultPlan = false;
      this.dialogRef.close(this.plan);
    }
  }

  onClose() {
    this.dialogRef.close();
  }
}
