import {NgModule} from '@angular/core';

import {RouterModule, Routes} from "@angular/router";
import {DashboardComponent} from "../../pages/dashboard/dashboard.component";
import {StorageComponent} from "../../pages/storage/storage.component";
import {ClientsComponent} from "../../pages/clients/clients.component";
import {SessionsComponent} from "../../pages/sessions/sessions.component";
import {AdminStorageComponent} from "../../pages/admin-storage/admin-storage.component";
import {SettingsComponent} from "../../pages/settings/settings.component";
import {UserService} from "../../pages/auth/registration/service/user.service";
import {AuthGuard} from "../../core/auth/auth.guard";
import {ProfileComponent} from "../../pages/profile/profile.component";
import {ProfileRoutingModule} from "../../pages/profile/profile-routing.module";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'dashboard'},
  {
    path: 'dashboard',
    component: DashboardComponent,
    loadChildren: () => import('../../pages/dashboard/dashboard.module').then(m => m.DashboardModule)
  },
  {
    path: 'storage',
    component: StorageComponent,
    loadChildren: () => import('../../pages/storage/storage.module').then(m => m.StorageModule)
  },
  {
    path: 'emeta-storage',
    component: AdminStorageComponent,
    loadChildren: () => import('../../pages/admin-storage/admin-storage.module').then(m => m.AdminStorageModule)
  },
  {
    path: 'sessions',
    component: SessionsComponent,
    loadChildren: () => import('../../pages/sessions/sessions.module').then(m => m.SessionsModule)
  },
  {
    path: 'clients',
    component: ClientsComponent,
    loadChildren: () => import('../../pages/clients/clients.module').then(m => m.ClientsModule)
  },
  {
    path: 'profile',
    component: ProfileComponent,
    loadChildren: () => import('../../pages/profile/profile-routing.module').then(m => m.ProfileRoutingModule)
  },
  {
    path: 'profile/:id',
    component: ProfileComponent,
    loadChildren: () => import('../../pages/profile/profile-routing.module').then(m => m.ProfileRoutingModule)
  },
  {
    path: 'settings',
    component: SettingsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkplaceRoutingModule {
}
