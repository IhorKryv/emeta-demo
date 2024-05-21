import {Component, OnInit, ViewChild} from '@angular/core';
import {TablePage} from "../../../../common/components/page-template/table-page";
import {MatPaginator} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {Payment} from "../model/payment";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {AddPaymentComponent} from "../add-payment/add-payment.component";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {PaymentService} from "../service/payment.service";

@Component({
  selector: 'orders',
  templateUrl: 'payments-list.component.html',
  styleUrls: ['payments-list.component.scss']
})

export class PaymentsListComponent extends TablePage<Payment> implements OnInit {

  public displayedColumns: string[] = ['index', 'date', 'client', 'description', 'type', 'status', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Payment> | undefined;

  constructor(dialog: MatDialog,
              public confirmation: ConfirmationService,
              private paymentService: PaymentService,
              private notificationService: NotificationService) {
    super(dialog);
  }

  ngOnInit() {
  }

  public addNewOrder() {
    this.openDialog(new Payment());
  }

  public editOrder(payment: Payment) {
    if (payment.id) {
      this.openDialog(payment);
    }
  }

  public deleteOrder(order: Payment) {

  }

  getMatPaginator(): MatPaginator | undefined {
    return this.paginator;
  }

  getMatTable(): MatTable<Payment> | undefined {
    return this.table;
  }

  loadData(searchText: string, page: number): void {

  }

  private openDialog(payment: Payment) {
    console.log(payment);
    this.createDialog(AddPaymentComponent, payment,
      data => this.paymentService.addPayment(data),
      (id, data) => this.paymentService.updatePayment(id, data),
      data => data.id ? this.notificationService.showNotification(NotificationType.SUCCESS, "Plan updated")
        : this.notificationService.showNotification(NotificationType.SUCCESS, "Plan created"),
      data => data.id ? this.notificationService.showNotification(NotificationType.ERROR, "Plan not updated") :
        this.notificationService.showNotification(NotificationType.ERROR, "Plan not created"));
  }
}
