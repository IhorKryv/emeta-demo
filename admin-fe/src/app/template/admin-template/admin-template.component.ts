import {Component, OnInit} from '@angular/core';
import {SideBarItem} from "../../../common/components/side-bar/side-bar.component";
import {MainPage} from "../../../common/components/page-template/main-page";
import {UserInfo} from "../../../common/components/user-info/user-info.component";
import {AuthService} from "../../core/auth/auth.service";

@Component({
  selector: 'admin-template',
  templateUrl: 'admin-template.component.html',
  styleUrls: ['admin-template.component.scss']
})

export class AdminTemplateComponent implements OnInit {

  public items: SideBarItem[] = [
    {order: 1, icon: 'home', url: 'home', label: $localize`:@@dashboard:Dashboard`},
    {order: 2, icon: 'receipt', url: 'payment-plans', label: $localize`:@@payment-plans:Payment plans`},
    {order: 3, icon: 'table_chart', url: 'storage', label: $localize`:@@storage:Storage`},
    {order: 4, icon: 'badge', url: 'workplace', label: $localize`:@@workplaces:Workplaces`},
    // {order: 5, icon: 'price_change', url: 'payments', label: 'Payments'},
    {order: 1, icon: 'security', url: 'security', label: $localize`:@@security:Security`, bottomPosition: true},
    {order: 2, icon: 'manage_accounts', url: 'users', label: $localize`:@@users:Users`, bottomPosition: true},
  ];

  public pageTitle: string = "";
  public userInfo: UserInfo | undefined;

  constructor(private authService: AuthService) {
    // This is intentional
  }

  ngOnInit() {
    setTimeout(() => {
      this.fillUserData();
    }, 100);
  }


  fillTemplate(componentRef: MainPage) {
    this.pageTitle = componentRef.getTitle();
  }

  fillUserData() {
    this.userInfo = {
      fullName: this.authService.userData?.username!
    };
  }

  logout = () => {
    this.authService.logout();
  }

}
