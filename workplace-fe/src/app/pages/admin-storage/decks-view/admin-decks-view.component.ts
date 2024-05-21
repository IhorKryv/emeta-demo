import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {DeckService} from "../../storage/service/deck.service";
import {Deck} from "../../storage/model/deck";
import {AdminDeckPreviewComponent} from "../../../../common/components/admin-deck-preview/admin-deck-preview.component";
import {DeviceDetectorService} from "ngx-device-detector";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'admin-decks-view-component',
  templateUrl: 'admin-decks-view.component.html',
  styleUrls: ['admin-decks-view.component.scss']
})

export class AdminDecksViewComponent implements OnInit {

  constructor(dialog: MatDialog,
              private _snackBar: MatSnackBar,
              private deckService: DeckService,
              private deviceService: DeviceDetectorService,
              private router: Router,
              private route: ActivatedRoute) {
    this.dialog = dialog;
    this.isMobile = this.deviceService.isMobile();
    this.getStats();
  }

  isMobile: boolean = false;

  dialog: MatDialog;

  decks: Deck[] = [];
  current: number = 0;
  limit: number = 0;
  showLoader: boolean = true;

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.deckService.getAllDecksFromAdmin().subscribe({
      next: (data) => {
        this.decks = [...data];
        this.showLoader = false;
      },
      error: err => console.log(err.error.error)
    })
  }

  public getCardBackImage(deck: Deck): string {
    return deck.cardBack && deck.cardBackUrl ? deck.cardBackUrl : "../../../assets/card-default.png";
  }

  openDeck = (deck: Deck) => {
    console.log(deck);
    this.router.navigate([deck.adminId], {relativeTo: this.route});
  }

  private getStats = () => {
    this.deckService.getAdminDeckStats().subscribe({
      next: (data) => {
        this.current = data["current"];
        this.limit = data["limit"];
      },
      error: err => console.log(err.error.error)
    })
  }
}
