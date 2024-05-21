import {Component, OnInit} from '@angular/core';
import {MainPage} from "../../../common/components/page-template/main-page";
import {ActivatedRoute, Router} from "@angular/router";
import {ProfileService} from "./profile.service";
import {PlanService} from "../plan-selector/plan.service";
import {MyPlan} from "../plan-selector/plan";

@Component({
  selector: 'profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.scss']
})

export class ProfileComponent implements OnInit, MainPage {

  tabs: any = [
    {name: 'general', title: 'Settings', icon: 'settings'},
    {name: 'banner', title: 'Banner Section', icon: 'settings_display'},
    {name: 'short-info', title: 'Short Info Section', icon: 'info_outline'},
    {name: 'schedule', title: 'Schedule Section', icon: 'schedule'},
    {name: 'my-decks', title: 'My Decks Section', icon: 'inbox'},
    {name: 'contact', title: 'Contacts Section', icon: 'contact_support'},
  ]
  constructor(private router: Router, private route: ActivatedRoute, private profileService: ProfileService, private planService: PlanService) {
  }

  myPlan: MyPlan = new MyPlan();
  initialized: boolean | undefined;
  currentId: string | undefined;
  profileAvailable: boolean = false;

  ngOnInit() {
    this.planService.getMyPlan().subscribe({
      next: (resp) => {
        this.myPlan = resp;
        if (this.myPlan.plan.macSettings.personalPage) {
          this.profileAvailable = true;
        }
        const id = this.route.snapshot.paramMap.get('id');
        this.initialized = id !== null;
        this.currentId = id!;
      }
    });
  }

  getTitle(): string {
    return "Profile";
  }

  openTab = (tab: string) => {
    this.router.navigate([tab], {relativeTo: this.route})
  }

  public initProfile = () => {
    this.profileService.createProfile().subscribe(resp => {
      if (resp) {
        this.router.navigate([resp], {relativeTo: this.route});
      }
    })
  }
}
