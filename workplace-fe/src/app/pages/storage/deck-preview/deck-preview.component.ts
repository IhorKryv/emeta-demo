import {Component, OnInit} from '@angular/core';
import {Deck} from "../model/deck";
import {DeckService} from "../service/deck.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'deck-preview',
  templateUrl: 'deck-preview.component.html'
})

export class DeckPreviewComponent implements OnInit {
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
