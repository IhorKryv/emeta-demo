import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Deck} from "../model/deck";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'add-deck-dialog',
  templateUrl: 'add-deck.component.html',
  styleUrls: ['add-deck.component.scss']
})

export class AddDeckDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddDeckDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public deck: Deck,
              private deviceService: DeviceDetectorService) {
    this.isMobile = deviceService.isMobile();
  }

  saveDisabled = !!this.deck.id;
  isMobile: boolean = false;

  public deckForm = new FormGroup({
    name: new FormControl(this.deck.name || '', Validators.required),
    description: new FormControl(this.deck.description || '', Validators.required)
  });

  onClose() {
    this.dialogRef.close();
  }

  onSave() {
    if (this.deckForm.invalid) {
      Object.keys(this.deckForm.controls)
        .forEach(value => this.deckForm.get(value)?.markAsTouched());
      return;
    }
    if (this.deckForm.valid) {
      this.deck.name = this.deckForm.get('name')?.value!;
      this.deck.description = this.deckForm.get('description')?.value!;
      this.dialogRef.close(this.deck);
    }
  }

  onValuesChange() {
    this.saveDisabled =
      this.deck.name === this.deckForm.get('name')?.value! &&
      this.deck.description === this.deckForm.get('description')?.value!
  }

}
