import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DeckService} from "../decks/service/deck.service";
import {Deck} from "../decks/model/deck";
import {FileHandle} from "../../../../common/components/dragDrop/dragDrop.directive";
import {Card} from "./model/card";
import {Pagination} from "../../../../common/components/pagination/pagination";
import {FileService} from "../../../../common/components/file/file.service";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";

@Component({
  selector: 'cards',
  templateUrl: 'cards.component.html',
  styleUrls: ['cards.component.scss']
})

export class CardsComponent implements OnInit {
  deck: Deck = new Deck();
  deckCards: Card[] = [];
  public pagination: Pagination = {
    page: 0,
    size: 24,
    totalItems: 24,
    totalPages: 1
  };

  constructor(private route: ActivatedRoute,
              private deckService: DeckService,
              private fileService: FileService,
              private confirmationService: ConfirmationService,
              private router: Router) {
  }

  loaderActive = false;
  showLoader: boolean = true;


  ngOnInit() {
    let deckId = this.route.snapshot.paramMap.get('id');
    if (deckId) {
      this.getDeckById(deckId);
    } else {
      //Need to fix
      this.router.navigate(["decks"]);
    }

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

  onCardSwap = (card: Card) => {
    this.confirmationService.show(() => this.makeCardAsCardback(card), undefined, {
      confirmationTitle:  $localize`:@@card-to-cardback-confirm-title:Set this card as a card back?`,
      confirmationMessage:  $localize`:@@card-to-cardback-confirm-message:Do you want set this card as card-back? After confirmation, selected card will be removed from cards list`
    })
  }

  private makeCardAsCardback = (card: Card) => {
    this.deckService.makeCardAsCardBack(this.deck.id!, card.id!, card.extension!).subscribe({
      next: () => {
        this.getDeckById(this.deck.id!);
      },
      error: (err) => console.log(err.error.error)
    });
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
    const file: File = event.target.files[0];
    if (file) {
      const formData = new FormData();
      formData.append("file", file);

      this.deckService.updateDeckCardBack(this.deck.id!, formData).subscribe({
        next: () => {
          this.getDeckById(this.deck.id!);
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
        this.pagination.page = resp.page;
        this.pagination.totalPages = resp.totalPages;
        this.pagination.size = resp.size;
        this.pagination.totalItems = resp.totalItems;
        this.deckCards = [...resp.items];
        this.showLoader = false;
      },
      error: (err) => console.log(err)
    })
  }

  uploadCards = (files: FileHandle[]) => {
    this.loaderActive = true;
    const formData = new FormData();
    for (let fileHandle of files) {
      formData.append("cards", fileHandle.file);
    }
    this.deckService.addCardsToDeck(this.deck.id!, formData).subscribe({
      next: () => {
        this.getDeckCards();
        this.loaderActive = false;
      }
    })
  }

  removeCard = (cardId: string, extension: string) => {
    this.deckService.removeCard(this.deck.id!, cardId, extension).subscribe({
      next: () => this.getDeckCards(),
      error: (err) => console.log(err)
    })
  }
}
