import {Component, OnInit, ViewChild} from '@angular/core';
import {TablePage} from "../../../../common/components/page-template/table-page";
import {Deck} from "./model/deck";
import {MatPaginator} from "@angular/material/paginator";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {MatTable} from "@angular/material/table";
import {DeckService} from "./service/deck.service";
import {AddCategoryDialogComponent} from "../categories/add-category-dialog/add-category.component";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {AddDeckDialogComponent} from "./add-deck-dialog/add-deck.component";
import {Router} from "@angular/router";

@Component({
  selector: 'deck',
  templateUrl: 'deck.component.html'
})

export class DeckComponent extends TablePage<Deck> implements OnInit {
  constructor(dialog: MatDialog,
              private router: Router,
              private deckService: DeckService,
              private _snackBar: MatSnackBar,
              public confirmationService: ConfirmationService,
              private notificationService: NotificationService) {
    super(dialog);
  }

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Deck> | undefined;

  public displayedColumns: string[] = ['index', 'name', 'description', 'categories', 'cardsCount', 'available', 'premium', 'actions'];
  public searchValue = "";

  ngOnInit() {
    this.loadData();
  }

  addNewDeck() {
    this.openDialog(new Deck());
  }

  editDeck(deck: Deck) {
    this.openDialog(deck);
  }

  onDeckDelete(deck: Deck) {
    this.confirmationService.show(() => this.deleteDeck(deck.id!));
  }

  onAvailabilityChange(deck: Deck) {
    this.confirmationService.show(() => this.changeDeckAvailability(deck), undefined, {
      confirmationTitle: $localize`:@@deck-availability-confirm-title:Deck availability change`,
      confirmationMessage: $localize`:@@deck-availability-confirm-message:Do you want to ${deck.available ? "hide" : "show"} this deck?`
    })
  }

  onPremiumStatusChange(deck: Deck) {
    this.confirmationService.show(() => this.changeDeckPremiumStatus(deck), undefined, {
      confirmationTitle: $localize`:@@deck-premium-confirm-title:Deck premium status change`,
      confirmationMessage: $localize`:@@deck-premium-confirm-message:Do you want to make this deck ${deck.premium ? "premium" : "public"} ?`
    })
  }

  private deleteDeck = (id: string) => {
    this.deckService.deleteDeck(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@deck-delete-success:The deck and its cards have been successfully deleted`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't delete`, err.error.error)
    })
  }

  private changeDeckAvailability = (deck: Deck) => {
    this.deckService.updateDeck(deck.id!, deck).subscribe({
      next: () => this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@deck-update-success:The deck has been updated`),
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`, err.error.error)
    })
  }

  private changeDeckPremiumStatus = (deck: Deck) => {
    this.deckService.updateDeck(deck.id!, deck).subscribe({
      next: () => this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@deck-update-success:The deck has been updated`),
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`, err.error.error)
    })
  }

  getMatPaginator(): MatPaginator | undefined {
    return undefined;
  }

  getMatTable(): MatTable<Deck> | undefined {
    return undefined;
  }

  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.deckService.getAllDecks(searchText, page));
  }

  openDeck = (deck: Deck) => {
    this.router.navigate([deck.id, "cards"]);
  }

  private openDialog(deck: Deck) {
    this.createDialog(AddDeckDialogComponent, deck,
      data => this.deckService.createDeck(data),
      (id, data) => this.deckService.updateDeck(id, data),
      data => {
        this.loadData("", this.pagination.page);
        !data.id
          ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@deck-create-success:A new deck has been added to app`)
          : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@deck-update-success:The deck has been updated`)
      },
      (data, err) => {
        this.loadData("", this.pagination.page);
        !data.id
          ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't create`, err.error.error)
          : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`, err.error.error)
      },
    );
  }
}
