import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {PermissionsComponent} from "./permissions/permissions.component";
import {RolesComponent} from "./roles/roles.component";
import {PermissionService} from "./permissions/service/permission.service";
import {RoleService} from "./roles/service/role.service";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'permissions'},
  {path: 'permissions', component: PermissionsComponent},
  {path: 'roles', component: RolesComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
    PermissionService,
    RoleService
  ]
})
export class SecurityRoutingModule {
}
