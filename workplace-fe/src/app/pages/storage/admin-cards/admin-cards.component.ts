import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DeckService} from "../service/deck.service";
import {Pagination} from "../../../../common/components/pagination/pagination";
import {FileHandle} from "../../../../common/components/dragDrop/dragDrop.directive";
import {Deck} from "../model/deck";
import {Card} from "../model/card";
import {environment} from "../../../../environments/environment";


@Component({
  selector: 'cards',
  templateUrl: 'admin-cards.component.html',
  styleUrls: ['admin-cards.component.scss']
})

export class AdminCardsComponent implements OnInit {
  constructor(private route: ActivatedRoute,
              private deckService: DeckService,
              private router: Router) {
  }

  deck: Deck = history.state.deck;
  deckCards: Card[] = [];

  ngOnInit() {
    console.log(this.deck);

  }

  getDeckById = (id: string) => {
    this.deckService.getSingleDeck(id).pipe().subscribe({
      next: data => {
        this.deck = data;
        this.getDeckCards();
      },
      //Need to fix
      error: () => this.router.navigate(["decks"])
    })
  }

  removeCardBackFromDeck() {
    this.deckService.removeCardbackFromDeck(this.deck.id!).subscribe({
      next: () => {
        this.getDeckById(this.deck.id!);
        console.log("REMOVED")
      },
      error: (err) => console.log(err)
    })
  }

  onFileSelected(event: any) {
    const file:File = event.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append("file", file);

      this.deckService.updateDeckCardBack(this.deck.id!, formData).subscribe({
        next: () => {
          console.log("UPLOADED");
          this.getDeckById(this.deck.id!);
          console.log(this.deck);
        },
        error: err => console.log(err)
      })
    }
  }

  getDeckCards = (event?: any) => {
    let page = 0;
    if (event) {
      page = event.pageIndex;
    }
    this.deckService.getAllCardsFromDeck(this.deck.id!, "", page, 24).subscribe({
      next: (resp) => {
        this.deckCards = [...resp.items];
      },
      error: (err) => console.log(err)
    })
  }

  uploadCards = (files: FileHandle[]) => {
    const formData = new FormData();
    for (let fileHandle of files) {
      formData.append("cards", fileHandle.file);
    }
    this.deckService.addCardsToDeck(this.deck.id!, formData).subscribe({
      next: () => {
        this.getDeckCards();
      }
    })
  }

  removeCard = (cardId: string) => {
    this.deckService.removeCard(this.deck.id!, cardId).subscribe({
      next: () => this.getDeckCards(),
      error: (err) => console.log(err)
    })
  }
}
