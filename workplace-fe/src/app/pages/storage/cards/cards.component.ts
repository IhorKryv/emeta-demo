import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DeckService} from "../service/deck.service";
import {Pagination} from "../../../../common/components/pagination/pagination";
import {FileHandle} from "../../../../common/components/dragDrop/dragDrop.directive";
import {Deck} from "../model/deck";
import {Card} from "../model/card";
import {environment} from "../../../../environments/environment";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {DeviceDetectorService} from "ngx-device-detector";


@Component({
  selector: 'cards',
  templateUrl: 'cards.component.html',
  styleUrls: ['cards.component.scss', 'cards-mobile.component.scss']
})

export class CardsComponent implements OnInit {
  constructor(private route: ActivatedRoute,
              private deckService: DeckService,
              private confirmationService: ConfirmationService,
              private notificationService: NotificationService,
              private router: Router,
              private deviceService: DeviceDetectorService) {
    this.isMobile = deviceService.isMobile();
  }

  deck: Deck = new Deck();
  cardBackUrl: string = "";
  deckCards: Card[] = [];

  isMobile: boolean = false;

  public pagination: Pagination = {
    page: 0,
    size: 24,
    totalItems: 24,
    totalPages: 1
  };

  ngOnInit() {
    let deckId = this.route.snapshot.paramMap.get('id');
    if (deckId) {
      this.getDeckById(deckId);
    } else {
      //Need to fix
      this.router.navigate(["decks"]);
    }

  }

  getImagePath (filename: string): string {
    return environment.serverURL + "api/file/get/deck/" + this.deck.id! + "/card/" + filename;
  }

  getDeckById = (id: string) => {
    this.deckService.getSingleDeck(id).pipe().subscribe({
      next: data => {
        this.deck = data;
        this.getDeckCards();
        if (data.cardBack) {
          this.cardBackUrl = this.getImagePath(data.cardBack);
        } else {
          this.cardBackUrl = "";
        }
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

  onCardSwap = (card: Card) => {
    this.confirmationService.show(() => this.makeCardAsCardback(card), undefined, {
      confirmationTitle: $localize`:@@card-to-cardback-confirm-title:Set this card as a card back?`,
      confirmationMessage: $localize`:@@card-to-cardback-confirm-message:Do you want set this card as card-back? After confirmation, selected card will be removed from cards list`
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
        this.pagination.page = resp.page;
        this.pagination.totalPages = resp.totalPages;
        this.pagination.size = resp.size;
        this.pagination.totalItems = resp.totalItems;
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

  uploadCardsMobile = (event: any) => {
    const formData = new FormData();
    for (let file of event.target.files) {
      formData.append("cards", file);
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
