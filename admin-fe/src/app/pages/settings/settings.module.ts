import {NgModule} from '@angular/core';

import {SettingsComponent} from './settings.component';
import {CommonModule} from "@angular/common";

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [SettingsComponent],
  declarations: [SettingsComponent],
  providers: [],
})
export class SettingsModule {
}
