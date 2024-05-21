import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Category} from "../../categories/model/category";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Deck} from "../model/deck";
import {CategoryService} from "../../categories/service/category.service";

@Component({
  selector: 'add-deck-dialog',
  templateUrl: 'add-deck.component.html',
  styleUrls: ['add-deck.component.scss']
})

export class AddDeckDialogComponent {
  saveDisabled = !!this.deck.id;
  availableCategories: Category[] = [];
  public deckForm = new FormGroup({
    name: new FormControl(this.deck.name || '', Validators.required),
    description: new FormControl(this.deck.description || '', Validators.required),
    categories: new FormControl(this.deck.categories?.map(cat => cat.id)),
  });

  constructor(public dialogRef: MatDialogRef<AddDeckDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public deck: Deck,
              private categoryService: CategoryService) {
    console.log(deck);
    this.categoryService.getAllCategoriesForSelection().subscribe(content => this.availableCategories = content);
  }

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
      if (this.deckForm.get('categories')?.value) {
        this.deck.categories = this.deckForm.get('categories')?.value?.map(categoryId => {
          return {
            id: categoryId
          }
        });
      }
      this.dialogRef.close(this.deck);
    }
  }

  onValuesChange() {
    this.saveDisabled =
      this.deck.name === this.deckForm.get('name')?.value! &&
      this.deck.description === this.deckForm.get('description')?.value! &&
      this.arrayEquals(this.deck.categories?.map(cat => cat.id), this.deckForm.get('categories')?.value)
  }

  public getCategoryName(id: string | undefined): string {
    let category = this.availableCategories.find(c => c.id === id);
    return category ? category.name! : "";
  }


  private arrayEquals = (a: any[] | undefined, b: any[] | undefined | null) => {
    return Array.isArray(a) &&
      Array.isArray(b) &&
      a.length === b.length &&
      a.every(v => b.includes(v));
  }
}
