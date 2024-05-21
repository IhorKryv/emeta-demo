import {Component, OnInit} from '@angular/core';
import {TablePage} from "../../../../common/components/page-template/table-page";
import {Order} from "../model/order";
import {MatPaginator} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'orders',
  templateUrl: 'orders.component.html',
  styleUrls: ['orders.component.scss']
})

export class OrdersComponent extends TablePage<Order> implements OnInit {

  public displayedColumns: string[] = ['index', 'date', 'description', 'type', 'status', 'actions'];

  constructor(dialog: MatDialog) {
    super(dialog);
  }

  ngOnInit() {
  }

  public addNewOrder() {

  }

  public editOrder(order: Order) {

  }

  public deleteOrder(order: Order) {

  }

  getMatPaginator(): MatPaginator | undefined {
    return undefined;
  }

  getMatTable(): MatTable<Order> | undefined {
    return undefined;
  }

  loadData(searchText: string, page: number): void {
  }
}
