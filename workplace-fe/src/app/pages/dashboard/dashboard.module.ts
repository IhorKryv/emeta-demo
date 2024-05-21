import {NgModule} from '@angular/core';

import {DashboardComponent} from './dashboard.component';
import {CommonModule} from "@angular/common";
import {MatDividerModule} from "@angular/material/divider";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {SessionsService} from "../sessions/service/sessions.service";
import {CalendarModule} from "../../../common/components/calendar/calendar.module";
import {MatDialogModule} from "@angular/material/dialog";
import {CalendarItemComponent} from "./calendar-item-dialog/calendar-item.component";
import {SessionsModule} from "../sessions/sessions.module";

@NgModule({
    imports: [
        CommonModule,
        MatButtonModule,
        MatDividerModule,
        MatIconModule,
        CalendarModule,
        MatDialogModule,
        SessionsModule,
    ],
  exports: [DashboardComponent],
  declarations: [
    DashboardComponent,
    CalendarItemComponent
  ],
  providers: [SessionsService],
})
export class DashboardModule {
}
