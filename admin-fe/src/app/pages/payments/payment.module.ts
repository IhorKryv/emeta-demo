import {NgModule} from '@angular/core';

import {PaymentComponent} from './payment.component';
import {PaymentsListComponent} from "./payments-list/payments-list.component";
import {SearchFieldModule} from "../../../common/components/search-field/search-field.module";
import {MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatPaginatorModule} from "@angular/material/paginator";
import {CommonModule} from "@angular/common";
import {PaymentRoutingModule} from "./payment-routing.module";
import {AddPaymentComponent} from "./add-payment/add-payment.component";
import {MatDialogModule} from "@angular/material/dialog";
import {ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatInputModule} from "@angular/material/input";
import {AutocompleteModule} from "../../../common/components/autocomplete/autocomplete.module";
import {MatSelectModule} from "@angular/material/select";
import {TextUtilsModule} from "../../../common/components/text-utils/text-utils.module";
import {PaymentService} from "./service/payment.service";
import {ConfirmationModule} from "../../../common/components/confirmation/confirmation.module";
import {NotificationModule} from "../../../common/components/notification/notification.module";

@NgModule({
  imports: [
    PaymentRoutingModule,
    SearchFieldModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
    CommonModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSlideToggleModule,
    MatInputModule,
    AutocompleteModule,
    MatSelectModule,
    TextUtilsModule,
    ConfirmationModule,
    NotificationModule
  ],
  exports: [],
  declarations: [PaymentComponent, PaymentsListComponent, AddPaymentComponent],
  providers: [PaymentService],
})
export class PaymentModule {
}
