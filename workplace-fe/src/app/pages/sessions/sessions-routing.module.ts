import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {SessionsService} from "./service/sessions.service";
import {AvailableSessionsComponent} from "./available/available-sessions.component";
import {ArchivedSessionsComponent} from "./archived/archived-sessions.component";
import {ClientService} from "../clients/service/client.service";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'available'},
  {path: 'available', component: AvailableSessionsComponent},
  {path: 'archived', component: ArchivedSessionsComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  declarations: [],
  providers: [
    SessionsService,
    ClientService
  ],
})
export class SessionsRoutingModule {
}
