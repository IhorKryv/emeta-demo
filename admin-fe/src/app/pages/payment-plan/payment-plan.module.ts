import {NgModule} from '@angular/core';
import {PlansComponent} from "./plans/plans.component";
import {PreferenceComponent} from "./preferences/preference.component";
import {PaymentPlanComponent} from "./payment-plan.component";
import {PaymentPlanRoutingModule} from "./payment-plan-routing.module";
import {AddPreferenceDialogComponent} from "./add-preference-dialog/add-preference-dialog.component";
import {SearchFieldModule} from "../../../common/components/search-field/search-field.module";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatTabsModule} from "@angular/material/tabs";
import {CommonModule} from "@angular/common";
import {ConfirmationModule} from "../../../common/components/confirmation/confirmation.module";
import {AddPlanDialogComponent} from "./add-plan-dialog/add-plan-dialog.component";
import {MatChipsModule} from "@angular/material/chips";
import {MatSelectModule} from "@angular/material/select";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {NotificationModule} from "../../../common/components/notification/notification.module";
import {TextUtilsModule} from "../../../common/components/text-utils/text-utils.module";
import {LoaderModule} from "../../../common/components/loader/loader.module";

@NgModule({
    imports: [
        SearchFieldModule,
        ConfirmationModule,
        NotificationModule,
        PaymentPlanRoutingModule,
        MatDialogModule,
        MatInputModule,
        MatButtonModule,
        FormsModule,
        MatTableModule,
        MatIconModule,
        MatTabsModule,
        CommonModule,
        MatChipsModule,
        MatSelectModule,
        MatAutocompleteModule,
        DragDropModule,
        MatPaginatorModule,
        ReactiveFormsModule,
        MatCheckboxModule,
        MatSlideToggleModule,
        TextUtilsModule,
        LoaderModule
    ],
  exports: [],
  declarations: [
    PaymentPlanComponent,
    PlansComponent,
    PreferenceComponent,
    AddPreferenceDialogComponent,
    AddPlanDialogComponent
  ],
  providers: [],
})
export class PaymentPlanModule {
}
