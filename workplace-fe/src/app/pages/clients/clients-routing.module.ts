import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ClientService} from "./service/client.service";
import {ClientsListComponent} from "./clients-list/clients-list.component";
import {ClientRecordsService} from "./service/client-record.service";
import {ClientRecordsComponent} from "./client-records-list/client-records.component";


const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'all'},
  {path: 'all', component: ClientsListComponent},
  {path: ':id', component: ClientRecordsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  declarations: [],
  providers: [
    ClientService,
    ClientRecordsService
  ],
})
export class ClientsRoutingModule {
}
