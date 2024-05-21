import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DeckService} from "../../storage/service/deck.service";
import {Deck} from "../../storage/model/deck";

@Component({
  selector: 'deck-preview',
  templateUrl: 'deck-from-admin-preview.component.html'
})

export class DeckFromAdminPreviewComponent implements OnInit {
  constructor(private adminDeckService: DeckService, private route: ActivatedRoute, private router: Router) {
  }

  deck: Deck | undefined;
  showDeckData: boolean = false;
  ngOnInit() {
    let deckId = this.route.snapshot.paramMap.get('id');
    if (deckId) {
      this.adminDeckService.getSingleDeckFromAdmin(deckId).subscribe({
        next: (data) => {
          this.deck = data
          this.showDeckData = true;
        }
      })
    }
  }


}
