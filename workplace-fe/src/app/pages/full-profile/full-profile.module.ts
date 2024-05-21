import {NgModule} from '@angular/core';

import {FullProfileComponent} from './full-profile.component';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule
  ],
  exports: [FullProfileComponent],
  declarations: [FullProfileComponent],
  providers: [],
})
export class FullProfileModule {
}
