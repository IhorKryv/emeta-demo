import {NgModule} from '@angular/core';

import {WorkplaceTemplateComponent} from './workplace-template.component';
import {WorkplaceRoutingModule} from "./workplace-routing.module";
import {CommonModule} from "@angular/common";
import {SideBarModule} from "../../../common/components/side-bar/side-bar.module";
import {RouterModule} from "@angular/router";
import {HeaderModule} from "../../../common/components/header/header.module";
import {DashboardModule} from "../../pages/dashboard/dashboard.module";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {SettingsModule} from "../../pages/settings/settings.module";
import {ProfileModule} from "../../pages/profile/profile.module";
import {ProfileService} from "../../pages/profile/profile.service";

@NgModule({
  imports: [
    WorkplaceRoutingModule,
    CommonModule,
    SideBarModule,
    HeaderModule,
    RouterModule,
    DashboardModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    SettingsModule,
    ProfileModule
  ],
  exports: [],
  declarations: [WorkplaceTemplateComponent],
  providers: [ProfileService],
})
export class WorkplaceTemplateModule {
}
