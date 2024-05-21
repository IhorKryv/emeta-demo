import {NgModule} from '@angular/core';

import {ProfileComponent} from './profile.component';
import {CommonModule} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {BannerComponent} from "./builder/banner/banner.component";
import {RouterModule} from "@angular/router";
import {SideBarModule} from "../../../common/components/side-bar/side-bar.module";
import {MatIconModule} from "@angular/material/icon";
import {BannerService} from "./builder/banner/banner.service";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ShortInfoComponent} from "./builder/short-info/short-info.component";
import {ShortInfoService} from "./builder/short-info/short-info.service";
import {ScheduleComponent} from "./builder/schedule/schedule.component";
import {ScheduleService} from "./builder/schedule/schedule.service";
import {ProfileDeckService} from "./builder/my-decks/profile-deck.service";
import {ProfileDeckComponent} from "./builder/my-decks/profile-deck.component";
import {ProfileContactComponent} from "./builder/contacts/profile-contact.component";
import {ProfileContactService} from "./builder/contacts/profile-contact.service";
import {ProfileGeneralComponent} from "./builder/general/profile-general.component";

@NgModule({
  imports: [
    CommonModule,
    MatButtonModule,
    RouterModule,
    SideBarModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [],
  declarations: [
    ProfileComponent,
    BannerComponent,
    ShortInfoComponent,
    ScheduleComponent,
    ProfileDeckComponent,
    ProfileContactComponent,
    ProfileGeneralComponent
  ],
  providers: [
    BannerService,
    ShortInfoService,
    ScheduleService,
    ProfileDeckService,
    ProfileContactService
  ],
})
export class ProfileModule {
}
