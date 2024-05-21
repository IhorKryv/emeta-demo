import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {UserListComponent} from "../users/user-list/user-list.component";
import {WorkplaceListComponent} from "./workplace-list/workplace-list.component";
import {WorkplacePageComponent} from "./workplace-page/workplace-page.component";

const routes: Routes = [
  {path: '', component: WorkplaceListComponent},
  {path: 'new', component: WorkplacePageComponent},
  {path: ':id', component: WorkplacePageComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  declarations: [],
  providers: [],
})
export class WorkplaceRoutingModule {
}
