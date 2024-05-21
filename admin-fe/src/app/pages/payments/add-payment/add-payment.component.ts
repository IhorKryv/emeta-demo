import {Component, Inject} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {PaymentPlanService} from "../../payment-plan/payment-plan.service";
import {AutocompleteData} from "../../../../common/components/autocomplete/autocomplete.component";
import {Workplace} from "../../workplace/model/workplace";
import {Payment} from "../model/payment";
import {PaymentService} from "../service/payment.service";

@Component({
  selector: 'add-payment',
  templateUrl: 'add-payment.component.html'
})

export class AddPaymentComponent {

  paymentForm: FormGroup;
  workplaceAutocomplete: AutocompleteData<Workplace>;

  constructor(public dialogRef: MatDialogRef<AddPaymentComponent>,
              private paymentService: PaymentService, @Inject(MAT_DIALOG_DATA) public payment: Payment) {

    this.paymentForm = new FormGroup({
      description: new FormControl(this.payment.description),
      workplace: new FormControl(this.payment.workplace)
    })
    this.workplaceAutocomplete = new AutocompleteData<Workplace>(
      (<FormControl>this.paymentForm.controls['workplace']),
      item => { console.log(item); return item.firstName + ' ' + item.lastName},
      item => this.onWorkplaceChanged(item),
      (searchText, size) => this.paymentService.getWorkplaces({searchText: searchText, size: size})
    )
  }

  onWorkplaceChanged(workplace: Workplace): void {
    console.log(workplace);
    if (workplace.id) {
    }
  }

  onSave() {
    if (this.paymentForm.invalid) {
      Object.keys(this.paymentForm.controls)
        .forEach(value => this.paymentForm.get(value)?.markAsTouched());
      return;
    }
    if (this.paymentForm.valid) {
      this.dialogRef.close(this.payment);
    }
  }

  onClose() {
    this.dialogRef.close();
  }
}
