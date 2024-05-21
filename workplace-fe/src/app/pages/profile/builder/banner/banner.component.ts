import {Component, OnInit} from '@angular/core';
import {BannerService} from "./banner.service";
import {Banner} from "./banner";
import {debounceTime, Subject} from "rxjs";
import {ProfileService} from "../../profile.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'banner',
  templateUrl: 'banner.component.html',
  styleUrls: ['banner.component.scss']
})

export class BannerComponent implements OnInit {
  private inputChangeSubject = new Subject<any>();
  constructor(private bannerService: BannerService, private profileService: ProfileService, private route: ActivatedRoute) {
    this.inputChangeSubject
      .pipe(debounceTime(500))
      .subscribe(value => {
        if (value.target === "title") {
          this.bannerService.saveTitle(value.text || null).subscribe();
        }
        if (value.target === "info") {
          this.bannerService.saveInfo(value.text || null).subscribe();
        }
      });
  }

  banner: Banner = new Banner();
  mainImageURL: string | undefined;
  backgroundURL: string | undefined;
  ngOnInit() {
    this.bannerService.getBanner().subscribe(resp => {
      this.banner = resp;
      if (resp.background) {
        this.getImage("background", this.banner.background!, this.banner.profileId!)
      }
      if (resp.image) {
        this.getImage("main", this.banner.image!, this.banner.profileId!)
      }
    })
  }

  onTitleChange() {
    this.inputChangeSubject.next({target: "title", text: this.banner.title!});
  }

  onInfoChange() {
    this.inputChangeSubject.next({target: "info", text: this.banner.info!});
  }

  onMainImageSelected(event: any) {
    const file:File = event.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append("image", file);

      this.bannerService.saveImage(formData).subscribe({
        next: (image) => {
          this.mainImageURL = image.url;
        },
        error: err => console.log(err)
      })
    }
  }

  onBackgroundSelected(event: any) {
    const file:File = event.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append("image", file);

      this.bannerService.saveBackground(formData).subscribe({
        next: (image) => {
          this.backgroundURL = image.url;
        },
        error: err => console.log(err)
      })
    }
  }

  getImage = (target: string, name: string, profileId: string) => {
    this.profileService.getMyImageURLByName(profileId!, name).subscribe(image => {
      switch (target) {
        case "main":
          this.mainImageURL = image.url;
          break;
        case "background":
          this.backgroundURL = image.url;
          break;
      }
    })
  }

  removeBackgroundImage = () => {
    this.backgroundURL = undefined;
  }

  removeMainImage = () => {
    this.mainImageURL = undefined;
  }



}
