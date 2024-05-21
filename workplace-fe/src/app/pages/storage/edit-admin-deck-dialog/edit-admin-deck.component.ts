import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DeckService} from "../service/deck.service";
import {Deck} from "../model/deck";
import {FormControl, FormGroup} from "@angular/forms";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'edit-admin-deck',
  templateUrl: 'edit-admin-deck.component.html',
  styleUrls: ['edit-admin-deck.component.scss']
})

export class EditAdminDeckComponent {
  constructor(public dialogRef: MatDialogRef<EditAdminDeckComponent>,
              @Inject(MAT_DIALOG_DATA) public deck: Deck,
              private deckService: DeckService,
              private deviceService: DeviceDetectorService) {
    this.isMobile = this.deviceService.isMobile();
  }

  isMobile: boolean = false;
  deckCustomForm: FormGroup = new FormGroup<any>({
    customName: new FormControl(this.deck.customName || ''),
    customDescription: new FormControl(this.deck.customDescription || '')
  })

  onClose() {
    this.dialogRef.close();
  }

  onSave() {
    this.deck.customName = this.deckCustomForm.get('customName')?.value!;
    this.deck.customDescription = this.deckCustomForm.get('customDescription')?.value!;
    this.deckService.updateAdminDeck(this.deck.id!, this.deck).subscribe({
      next: () => this.dialogRef.close(true),
      error: err => console.log(err.error.error)
    });
  }
}
