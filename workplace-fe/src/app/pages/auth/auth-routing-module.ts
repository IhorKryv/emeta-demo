import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {NgModule} from "@angular/core";
import {AuthService} from "./service/auth.service";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
import {ResetPasswordService} from "./service/reset-password.service";
import {RegisterCompleteComponent} from "./register-complete/register-complete.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'login'},
  {path: 'login', component: LoginComponent},
  {path: 'sign-up', component: RegistrationComponent},
  {path: 'complete', component: RegisterCompleteComponent},
  {path: 'reset', component: ResetPasswordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
    AuthService,
    ResetPasswordService
  ]
})
export class AuthRoutingModule {
}
