import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {WorkplaceTemplateComponent} from "./template/workplace-template/workplace-template.component";
import {AuthComponent} from "./pages/auth/auth.component";
import {AuthGuard} from "./core/auth/auth.guard";
import {FullProfileComponent} from "./pages/full-profile/full-profile.component";
import {PlanSelectorComponent} from "./pages/plan-selector/plan-selector.component";
import {PlanPaymentComponent} from "./pages/plan-selector/plan-payment/plan-payment.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'workplace'},
  {path: 'auth',
    component: AuthComponent,
    loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'workplace',
    canActivate: [AuthGuard],
    component: WorkplaceTemplateComponent,
    loadChildren: () => import('./template/workplace-template/workplace-template.module').then(m => m.WorkplaceTemplateModule)
  },
  {
    path: 'plan-selector',
    canActivate: [AuthGuard],
    component: PlanSelectorComponent,
    loadChildren: () => import('./pages/plan-selector/plan-selector.module').then(m => m.PlanSelectorModule)
  },
  {
    path: 'plan-payment',
    canActivate: [AuthGuard],
    component: PlanPaymentComponent,
    loadChildren: () => import('./pages/plan-selector/plan-selector.module').then(m => m.PlanSelectorModule)
  },
  {
    path: 'full-profile/:id',
    component: FullProfileComponent,
    loadChildren: () => import('./pages/full-profile/full-profile.module').then(m => m.FullProfileModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
