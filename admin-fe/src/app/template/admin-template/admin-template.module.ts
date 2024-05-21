import {NgModule} from '@angular/core';

import {AdminTemplateComponent} from './admin-template.component';
import {SideBarModule} from "../../../common/components/side-bar/side-bar.module";
import {HeaderModule} from "../../../common/components/header/header.module";
import {RouterModule} from "@angular/router";
import {CommonModule} from "@angular/common";
import {AdminRoutingModule} from "./admin-routing.module";

@NgModule({
  imports: [
    AdminRoutingModule,
    CommonModule,
    SideBarModule,
    HeaderModule,
    RouterModule
  ],
  exports: [],
  declarations: [AdminTemplateComponent],
  providers: [],
})
export class AdminTemplateModule {
}
