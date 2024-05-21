import {Component, OnInit} from '@angular/core';
import {SideBarItem} from "../../../common/components/side-bar/side-bar.component";
import {UserInfo} from "../../../common/components/user-info/user-info.component";
import {MainPage} from "../../../common/components/page-template/main-page";
import {AuthService} from "../../pages/auth/service/auth.service";
import {DeviceDetectorService} from "ngx-device-detector";
import {ProfileService} from "../../pages/profile/profile.service";
import {MyPlan} from "../../pages/plan-selector/plan";
import {PlanService} from "../../pages/plan-selector/plan.service";
import {Router} from "@angular/router";

@Component({
  selector: 'workplace-template',
  templateUrl: 'workplace-template.component.html',
  styleUrls: ['workplace-template.component.scss']
})

export class WorkplaceTemplateComponent implements OnInit {

  public items: SideBarItem[] = [
    {order: 1, icon: 'home', url: 'dashboard', label: $localize`:@@dashboard:Calendar`},
    {order: 2, icon: 'storage', url: 'emeta-storage', label: $localize`:@@emeta-storage:EMeta Storage`},
    {order: 3, icon: 'table_chart', url: 'storage', label: $localize`:@@storage:Storage`},
    {order: 4, icon: 'library_books', url: 'sessions', label: $localize`:@@sessions:Sessions`},
    {order: 5, icon: 'groups', url: 'clients', label: $localize`:@@clients:Clients`},
    {order: 5, icon: 'web', url: 'profile', label: $localize`:@@profile:Personal Page Editor`},
    {order: 6, icon: 'settings', url: 'settings', label: $localize`:@@settings:Settings`, bottomPosition: true},
  ];

  public pageTitle: string = "";
  public userInfo: UserInfo | undefined;
  public profileId: string | undefined;
  public myPlan: MyPlan = new MyPlan();

  constructor(private authService: AuthService,
              private deviceService: DeviceDetectorService,
              private profileService: ProfileService,
              private planService: PlanService,
              private router: Router) {
    this.isMobile = this.deviceService.isMobile();
  }

  isMobile: boolean = false;
  ngOnInit() {
    this.profileService.getProfile().subscribe(resp => {
      if (resp) {
        let profileItem = this.items.find(i => i.url === 'profile');
        profileItem!.url = profileItem!.url + "/" + resp.id;
      }
    })
    this.planService.getMyPlan().subscribe({
      next: (resp) => this.myPlan = resp
    })
    setTimeout(() => {
      this.fillUserData();
    }, 100);
  }


  fillTemplate(componentRef: MainPage) {
    this.pageTitle = componentRef.getTitle();
  }

  fillUserData() {
    this.userInfo = {
      fullName: this.authService.userData?.fullName!,
    };
  }

  logout = () => {
    this.authService.logout();
  }

  calculateNumberOfDays(value: any): number {
    const startDate = new Date();
    const endDate = new Date(value);
    const oneDay = 24 * 60 * 60 * 1000; // Number of milliseconds in a day
    const startTimestamp = startDate.getTime();
    const endTimestamp = endDate.getTime();
    return Math.round(Math.abs((endTimestamp - startTimestamp) / oneDay));
  }

  openPlans = () => {
    this.router.navigate(["/plan-selector"]);
  }
}
