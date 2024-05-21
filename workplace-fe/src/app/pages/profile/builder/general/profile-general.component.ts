import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../../profile.service";
import {Profile} from "../my-decks/profile";
import {debounceTime, Subject} from "rxjs";
import {NotificationService} from "../../../../../common/components/notification/notification.service";
import {NotificationType} from "../../../../../common/components/notification/notification-type";

@Component({
  selector: 'profile-general',
  templateUrl: 'profile-general.component.html',
  styleUrls: ['profile-general.component.scss']
})

export class ProfileGeneralComponent implements OnInit {
  private inputChangeSubject = new Subject<any>();
  constructor(private profileService: ProfileService, private notificationService: NotificationService) {
    this.inputChangeSubject
      .pipe(debounceTime(500))
      .subscribe(value => {
        this.profileService.saveProfileUrl(value.text || null).subscribe({
          next: () => {},
          error: (err) => {
            if (err.status === 400) {
              notificationService.showNotification(NotificationType.ERROR, "Can't save", "Profile with current URL already exists")
            } else {
              notificationService.showNotification(NotificationType.ERROR, "Can't save", "Something went wrong on server side")
            }

          }
        });
      });
  }

  profile: Profile = new Profile();

  ngOnInit() {
    this.profileService.getProfile().subscribe({
      next: (resp) => this.profile = resp
    });
  }

  onUrlChange = () => {
    this.inputChangeSubject.next({text: this.profile.url!});
  }
}
