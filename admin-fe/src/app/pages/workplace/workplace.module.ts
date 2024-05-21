import {NgModule} from '@angular/core';

import {WorkplaceComponent} from './workplace.component';
import {WorkplaceRoutingModule} from "./workplace-routing.module";
import {WorkplaceListComponent} from './workplace-list/workplace-list.component';
import {WorkplacePageComponent} from './workplace-page/workplace-page.component';
import {SearchFieldModule} from "../../../common/components/search-field/search-field.module";
import {MatButtonModule} from "@angular/material/button";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatPaginatorModule} from "@angular/material/paginator";
import {CommonModule} from "@angular/common";
import {WorkplaceService} from "./service/workplace.service";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {AutocompleteModule} from "../../../common/components/autocomplete/autocomplete.module";
import {TextUtilsModule} from "../../../common/components/text-utils/text-utils.module";
import {MatSelectModule} from "@angular/material/select";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {NotificationModule} from "../../../common/components/notification/notification.module";
import {OrdersComponent} from "./orders/orders.component";
import {LoaderModule} from "../../../common/components/loader/loader.module";

@NgModule({
  imports: [
    WorkplaceRoutingModule,
    SearchFieldModule,
    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatPaginatorModule,
    CommonModule,
    MatGridListModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatAutocompleteModule,
    AutocompleteModule,
    TextUtilsModule,
    MatSelectModule,
    MatSlideToggleModule,
    FormsModule,
    NotificationModule,
    LoaderModule
  ],
  exports: [],
  declarations: [WorkplaceComponent, WorkplaceListComponent, WorkplacePageComponent, OrdersComponent],
  providers: [WorkplaceService],
})
export class WorkplaceModule {
}
