import {Component, OnInit, ViewChild} from '@angular/core';
import {TablePage} from "../../../../common/components/page-template/table-page";
import {Deck} from "../model/deck";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {MatTable} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {DeckService} from "../service/deck.service";
import {AddDeckDialogComponent} from "../add-deck-dialog/add-deck.component";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {ActivatedRoute, Router} from "@angular/router";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'decks',
  templateUrl: 'my-decks.component.html',
  styleUrls: ['my-decks.component.scss', 'my-decks-mobile.component.scss']
})

export class MyDecksComponent extends TablePage<Deck> implements OnInit {
  getMatTable(): MatTable<Deck> | undefined {
    return undefined;
  }

  getMatPaginator(): MatPaginator | undefined {
    return undefined;
  }

  constructor(dialog: MatDialog,
              private _snackBar: MatSnackBar,
              public confirmationService: ConfirmationService,
              private notificationService: NotificationService,
              private deckService: DeckService,
              private router: Router,
              private route: ActivatedRoute,
              private deviceService: DeviceDetectorService) {
    super(dialog);
    this.isMobile = deviceService.isMobile();
  }

  decks: Deck[] = [];
  isMobile: boolean = false;

  ngOnInit() {
    this.loadData();
  }

  addNewDeck() {
    this.openDialog(new Deck());
  }

  editDeck(deck: Deck) {
    this.openDialog(deck);
  }

  openDeck(deck: Deck) {
    this.router.navigate([deck.id!, "cards"], {relativeTo: this.route});
  }

  onDeckDelete(deck: Deck) {
    this.confirmationService.show(() => this.deleteDeck(deck.id!));
  }

  onAvailabilityChange(deck: Deck) {
    this.confirmationService.show(() => this.changeDeckAvailability(deck), () => this.resetAvailability(deck), {
      confirmationTitle: $localize`:@@myDeck-availability-confirm-title:Deck availability change`,
      confirmationMessage: $localize`:@@myDeck-availability-confirm-message:Do you want to ${deck.available! ? "show" : "hide"} this deck?`
    })
  }

  public getCardBackImage(deck: Deck): string {
    return deck.cardBack && deck.cardBackUrl ? deck.cardBackUrl : "../../../assets/card-default.png";
  }

  loadData = () => {
    this.deckService.getAllDecks().subscribe({
      next: (data) => {
        this.decks = [...data];
        this.showLoader = false;
      },
      error: err => console.log(err.error.error)
    });
  }

  private resetAvailability = (deck: Deck) => {
    deck.available = !deck.available;
  }

  private deleteDeck = (id: string) => {
    this.deckService.deleteDeck(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@myDeck-delete-success:The deck and its cards have been successfully deleted`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't Delete`, err.error.error)
    })
  }

  private changeDeckAvailability = (deck: Deck) => {
    this.deckService.updateDeck(deck.id!, deck).subscribe({
      next: () => this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@myDeck-update-success:The deck has been updated`),
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't Update`, err.error.error)
    })
  }

  private openDialog(deck: Deck) {
    this.createDialog(AddDeckDialogComponent, deck,
      data => this.deckService.createDeck(data),
      (id, data) => this.deckService.updateDeck(id, data),
      data => {
        this.loadData()
        !data.id
          ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@myDeck-create-success:A new deck has been added to app`)
          : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@myDeck-update-success:The deck has been updated`)
      },
      (data, err) => {
        this.loadData()
        !data.id
          ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't Create`, err.error.error)
          : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't Update`, err.error.error)
      },
      this.isMobile ? "100vw" : "30vw",
      this.loadData
    );
  }
}
