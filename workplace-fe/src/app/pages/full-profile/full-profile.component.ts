import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FullProfile} from "./full-profile";
import {FullProfileService} from "./full-profile.service";
import {ProfileService} from "../profile/profile.service";
import {ProfileContact} from "../profile/builder/contacts/profile-contact";
import {ProfileDeck} from "../profile/builder/my-decks/profile-deck";
import {Banner} from "../profile/builder/banner/banner";
import {Schedule} from "../profile/builder/schedule/schedule";
import {ShortInfo} from "../profile/builder/short-info/short-info";

@Component({
  selector: 'full-profile',
  templateUrl: 'full-profile.component.html',
  styleUrls: [
    'full-profile.component.scss',
    'short-info.component.scss',
    'schedule.component.scss',
    'banner.component.scss',
    'decks.component.scss',
    'contact.component.scss',
    'mobile-banner.component.scss',
    'mobile-short-info.component.scss',
    'mobile-schedule.component.scss',
    'mobile-deck.component.scss',
    'mobile-contact.component.scss'
  ],
})

export class FullProfileComponent implements OnInit {

  constructor(private route: ActivatedRoute, private fullProfileService: FullProfileService, private profileService: ProfileService) {
  }

  fullProfile: FullProfile = new FullProfile();
  currentDeckIndex: number = 0;

  mobileInfoCardIndex: number = 0;
  mobileDeckIndex: number = 0;

  backgroundImageURL: string | undefined;
  bannerImage: string | undefined;

  ngOnInit() {
    this.fullProfile.profileContact = new ProfileContact();
    this.fullProfile.profileDeck = new ProfileDeck();
    this.fullProfile.banner = new Banner();
    this.fullProfile.schedule = new Schedule();
    this.fullProfile.shortInfo = new ShortInfo();
    const id = this.route.snapshot.paramMap.get("id");
    this.fullProfileService.getFullProfile(id!).subscribe({
      next: (profile) => {
        this.fullProfile = profile;
        if (profile?.banner?.background) {
          this.profileService.getMyImageURLByName(profile.profileId!, profile.banner.background!).subscribe({
            next: (resp) => this.backgroundImageURL = resp.url
          });
        }
        if (profile?.banner?.image) {
          this.profileService.getMyImageURLByName(profile.profileId!, profile.banner.image!).subscribe({
            next: (resp) => this.bannerImage = resp.url
          });
        }
      }
    })
  }

  slideRight = () => {
    if (this.currentDeckIndex === this.fullProfile.profileDeck.decks.length - 1) {
      this.currentDeckIndex = 0;
    } else {
      this.currentDeckIndex++;
    }
  }

  sliderLeft = () => {
    if (this.currentDeckIndex === 0) {
      this.currentDeckIndex = this.fullProfile.profileDeck.decks.length - 1;
    } else {
      this.currentDeckIndex--;
    }
  }

  slideRightMobile = (section: string) => {
    switch (section) {
      case "info":
        if (this.mobileInfoCardIndex === this.fullProfile!.shortInfo!.cards!.length - 1) {
          this.mobileInfoCardIndex = 0;
        } else {
          this.mobileInfoCardIndex++;
        }
        break;
      case "decks":
        if (this.mobileDeckIndex === this.fullProfile!.profileDeck!.decks!.length - 1) {
          this.mobileDeckIndex = 0;
        } else {
          this.mobileDeckIndex++;
        }
        break;
    }
  }

  slideLeftMobile = (section: string) => {
    switch (section) {
      case "info":
        if (this.mobileInfoCardIndex === 0) {
          this.mobileInfoCardIndex = this.fullProfile!.shortInfo!.cards!.length - 1;
        } else {
          this.mobileInfoCardIndex--;
        }
        break;
      case "decks":
        if (this.mobileDeckIndex === 0) {
          this.mobileDeckIndex = this.fullProfile!.profileDeck!.decks!.length - 1;
        } else {
          this.mobileDeckIndex--;
        }
        break;
    }
  }

  getScheduleDay(key: string): string {
    switch (key) {
      case "1":
        return "Monday";
      case "2":
        return "Tuesday";
      case "3":
        return "Wednesday";
      case "4":
        return "Thursday";
      case "5":
        return "Friday";
      case "6":
        return "Saturday";
      case "7":
        return "Sunday";
      default:
        return "";
    }
  }


  protected readonly Object = Object;
}
