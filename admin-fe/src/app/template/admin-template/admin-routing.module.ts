import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {HomeComponent} from "../../pages/home/home.component";
import {PaymentPlanComponent} from "../../pages/payment-plan/payment-plan.component";
import {SecurityComponent} from "../../pages/security/security.component";
import {SettingsComponent} from "../../pages/settings/settings.component";
import {UsersComponent} from "../../pages/users/users.component";
import {StorageComponent} from "../../pages/storage/storage.component";
import {WorkplaceComponent} from "../../pages/workplace/workplace.component";
import {PaymentComponent} from "../../pages/payments/payment.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'home'},
  {
    path: 'home',
    component: HomeComponent,
    loadChildren: () => import('../../pages/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'payment-plans',
    component: PaymentPlanComponent,
    loadChildren: () => import('../../pages/payment-plan/payment-plan.module').then(m => m.PaymentPlanModule)
  },
  {
    path: 'security',
    component: SecurityComponent,
    loadChildren: () => import('../../pages/security/security.module').then(m => m.SecurityModule)
  },
  {
    path: 'users',
    component: UsersComponent,
    loadChildren: () => import('../../pages/users/users.module').then(m => m.UsersModule)
  },
  {
    path: 'storage',
    component: StorageComponent,
    loadChildren: () => import('../../pages/storage/storage.module').then(m => m.StorageModule)
  },
  {
    path: 'workplace',
    component: WorkplaceComponent,
    loadChildren: () => import('../../pages/workplace/workplace.module').then(m => m.WorkplaceModule)
  },
  {
    path: 'payments',
    component: PaymentComponent,
    loadChildren: () => import('../../pages/payments/payment.module').then(m => m.PaymentModule)
  },
  {
    path: 'settings',
    component: SettingsComponent,
    loadChildren: () => import('../../pages/settings/settings.module').then(m => m.SettingsModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
