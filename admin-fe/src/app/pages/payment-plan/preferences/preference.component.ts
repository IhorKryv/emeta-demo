import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTable} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {AddPreferenceDialogComponent} from "../add-preference-dialog/add-preference-dialog.component";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {Preference} from "../model/preference";
import {MatPaginator} from "@angular/material/paginator";
import {PaymentPlanService} from "../payment-plan.service";
import {TablePage} from "../../../../common/components/page-template/table-page";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {NotificationType} from "../../../../common/components/notification/notification-type";

@Component({
  selector: 'preferences',
  templateUrl: 'preference.component.html',
  styleUrls: ['preference.component.scss']
})

export class PreferenceComponent extends TablePage<Preference> implements OnInit {

  constructor(dialog: MatDialog,
              public confirmation: ConfirmationService,
              private paymentPlanService: PaymentPlanService,
              private notificationService: NotificationService) {
    super(dialog);
  }

  public displayedColumns: string[] = ['index', 'name', 'key', 'description', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Preference> | undefined;

  getMatTable(): MatTable<Preference> | undefined {
    return this.table;
  }

  getMatPaginator(): MatPaginator | undefined {
    return this.paginator;
  }

  addNewItem() {
    this.openDialog(new Preference());
  }

  editItem(item: Preference) {
    this.openDialog(item);
  }

  deleteItem(item: Preference) {
    this.confirmation.show(() => {
      this.removeRow(item,
        id => this.paymentPlanService.deleteItem(id),
        () => this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@preference-delete-success:The preference has been deleted`),
        (data, err) => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't delete`, err.error));
    })
  }

  ngOnInit() {
    this.loadData();
  }

  loadData(searchText: string = "", page: number = 0) {
    this.loadTable(() => this.paymentPlanService.getItems(searchText, page));
  }

  private openDialog(item: Preference) {
    this.createDialog(AddPreferenceDialogComponent, item,
      data => this.paymentPlanService.addItem(data),
      (id, data) => this.paymentPlanService.updateItem(id, data),
      data => data.id
        ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@preference-update-success:The preference has been updated`)
        : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@preference-create-success:A new preference has been created`),
      data => data.id
        ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`) :
        this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't create`));
  }
}
