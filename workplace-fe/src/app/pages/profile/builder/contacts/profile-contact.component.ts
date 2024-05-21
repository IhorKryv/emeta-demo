import {Component, OnInit} from '@angular/core';
import {ProfileContactService} from "./profile-contact.service";
import {ProfileContact} from "./profile-contact";
import {debounceTime, Subject} from "rxjs";

@Component({
  selector: 'profile-contact',
  templateUrl: 'profile-contact.component.html',
  styleUrls: ['profile-contact.component.scss']
})

export class ProfileContactComponent implements OnInit {
  private inputChangeSubject = new Subject<any>();
  constructor(private profileContactService: ProfileContactService) {
    this.inputChangeSubject
      .pipe(debounceTime(500))
      .subscribe(value => {
        if (value.target === "title") {
          this.profileContactService.saveTitle(value.text || null).subscribe();
        }
        if (value.target === "short-text") {
          this.profileContactService.saveShortText(value.text || null).subscribe();
        }
        if (value.target === "email") {
          this.profileContactService.saveEmail(value.text || null).subscribe();
        }
        if (value.target === "phone") {
          this.profileContactService.savePhone(value.text || null).subscribe();
        }
      });
  }
  profileContact: ProfileContact = new ProfileContact();
  ngOnInit() {
    this.profileContactService.getProfileContact().subscribe({
      next: (resp) => {
        this.profileContact = resp;
      }
    })
  }

  onTitleChange() {
    this.inputChangeSubject.next({target: "title", text: this.profileContact.title!});
  }

  onShortTextChange() {
    this.inputChangeSubject.next({target: "short-text", text: this.profileContact.shortText!});
  }

  onEmailChange() {
    this.inputChangeSubject.next({target: "email", text: this.profileContact.email!});
  }

  onPhoneChange() {
    this.inputChangeSubject.next({target: "phone", text: this.profileContact.phone!});
  }
}
