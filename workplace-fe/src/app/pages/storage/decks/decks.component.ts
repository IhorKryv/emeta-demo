import {Component, OnInit} from '@angular/core';
import {Deck} from "../model/deck";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {DeckService} from "../service/deck.service";
import {EditAdminDeckComponent} from "../edit-admin-deck-dialog/edit-admin-deck.component";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {AdminDeckPreviewComponent} from "../../../../common/components/admin-deck-preview/admin-deck-preview.component";
import {DeviceDetectorService} from "ngx-device-detector";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'decks',
  templateUrl: 'decks.component.html',
  styleUrls: ['decks.component.scss']
})

export class DecksComponent implements OnInit {
  constructor(dialog: MatDialog,
              private _snackBar: MatSnackBar,
              private deckService: DeckService,
              private notificationService: NotificationService,
              private deviceService: DeviceDetectorService,
              private router: Router,
              private route: ActivatedRoute) {
    this.dialog = dialog;
    this.isMobile = this.deviceService.isMobile();
  }

  isMobile: boolean = false;

  dialog: MatDialog;

  decks: Deck[] = [];
  showLoader: boolean = true;

  ngOnInit() {
    this.loadData();
  }

  private loadData() {
    this.deckService.getAllSelectedAdminDecks().subscribe({
      next: (data) => {
        this.decks = [...data];
        this.showLoader = false;
      },
      error: err => console.log(err.error.error)
    })
  }

  getCardBackImage(deck: Deck): string {
    return deck.cardBack && deck.cardBackUrl ? deck.cardBackUrl : "../../../assets/card-default.png";
  }

  openDeck = (deck: Deck) => {
    this.router.navigate([deck.adminId + "/cards"], {relativeTo: this.route});
  }

  public editDeck(deck: Deck) {
    const dialogRef = this.dialog.open(EditAdminDeckComponent, {
      width: this.isMobile ? "100vw" : "30vw",
      data: {...deck},
    });
    dialogRef.afterClosed().subscribe((res: boolean) => {
      if (res) {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@admin-deck-update-success:EMeta Deck has been updated`)
        this.loadData();
      }
    });
  }

}
