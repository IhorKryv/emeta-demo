import {RouterModule, Routes} from "@angular/router";

import {NgModule} from "@angular/core";
import {BannerComponent} from "./builder/banner/banner.component";
import {ShortInfoComponent} from "./builder/short-info/short-info.component";
import {ScheduleComponent} from "./builder/schedule/schedule.component";
import {ProfileDeckComponent} from "./builder/my-decks/profile-deck.component";
import {ProfileContactComponent} from "./builder/contacts/profile-contact.component";
import {ProfileGeneralComponent} from "./builder/general/profile-general.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'general'},
  {path: 'general', component: ProfileGeneralComponent},
  {path: 'banner', component: BannerComponent},
  {path: 'short-info', component: ShortInfoComponent},
  {path: 'schedule', component: ScheduleComponent},
  {path: 'my-decks', component: ProfileDeckComponent},
  {path: 'contact', component: ProfileContactComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule {
}
