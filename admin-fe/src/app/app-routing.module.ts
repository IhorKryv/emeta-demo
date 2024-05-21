import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminTemplateComponent} from "./template/admin-template/admin-template.component";
import {AuthTemplateComponent} from "./template/auth-template/auth-template.component";
import {AuthGuard} from "./core/auth/auth.guard";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'admin'},
  {path: 'auth',
    component: AuthTemplateComponent,
    loadChildren: () => import('./template/auth-template/auth-template.module').then(m => m.AuthTemplateModule)
  },
  {
    path: 'admin',
    canActivate: [AuthGuard],
    component: AdminTemplateComponent,
    loadChildren: () => import('./template/admin-template/admin-template.module').then(m => m.AdminTemplateModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
