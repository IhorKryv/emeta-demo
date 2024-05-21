import {Component, OnInit} from '@angular/core';
import {ProfileDeckService} from "./profile-deck.service";
import {ProfileDeck, ProfileDeckItem} from "./profile-deck";
import {debounceTime, Subject} from "rxjs";

@Component({
  selector: 'profile-deck',
  templateUrl: 'profile-deck.component.html',
  styleUrls: ['profile-deck.component.scss']
})

export class ProfileDeckComponent implements OnInit {
  private inputChangeSubject = new Subject<any>();
  constructor(private profileDeckService: ProfileDeckService) {
    this.inputChangeSubject
      .pipe(debounceTime(500))
      .subscribe(value => {
        if (value.target === "title") {
          this.profileDeckService.saveTitle(value.text || null).subscribe();
        }
        if (value.target === "info") {
          this.profileDeckService.saveInfo(value.text || null).subscribe();
        }
        if (value.target === "item-title") {
          this.profileDeckService.saveDeckItemTitle(value.id, value.text || null).subscribe();
        }
        if (value.target === "item-info") {
          this.profileDeckService.saveDeckItemInfo(value.id,value.text || null).subscribe();
        }
      });
  }

  profileDeck: ProfileDeck = new ProfileDeck();

  ngOnInit() {
    this.profileDeckService.getProfileDeck().subscribe({
      next: (resp) => {
        this.profileDeck = resp;
      }
    })
  }

  addDeckItem = () => {
    this.profileDeckService.addDeckItem().subscribe({
      next: (resp) => {
        this.profileDeck.decks.push(resp);
      },
      error: () => {}
    })
  }

  onTitleChange() {
    this.inputChangeSubject.next({target: "title", text: this.profileDeck.title!});
  }

  onInfoChange() {
    this.inputChangeSubject.next({target: "info", text: this.profileDeck.info!});
  }

  onDeckTitleChange(id: string, value: string) {
    this.inputChangeSubject.next({id: id, target: "item-title", text: value});
  }

  onDeckInfoChange(id: string, value: string) {
    this.inputChangeSubject.next({id: id, target: "item-info", text: value});
  }

  onMainImageSelected(event: any, item: ProfileDeckItem) {
    const file:File = event.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append("image", file);

      this.profileDeckService.saveDeckItemImage(item.id!, formData).subscribe({
        next: (image) => {
          item.image = image.url;
        },
        error: err => console.log(err)
      })
    }
  }
}
