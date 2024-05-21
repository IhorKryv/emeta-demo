import {Component, OnInit, ViewChild} from '@angular/core';
import {ClientService} from "../service/client.service";
import {MatDialog} from "@angular/material/dialog";
import {TablePage} from "../../../../common/components/page-template/table-page";
import {MatTable} from "@angular/material/table";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Client} from "../model/client";
import {AddClientComponent} from "../add-client-dialog/add-client.component";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {DeviceDetectorService} from "ngx-device-detector";
import {ClientRecordsComponent} from "../client-records-list/client-records.component";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'clients-list',
  templateUrl: 'clients-list.component.html',
  styleUrls: ['clients-list.component.scss', 'clients-list-mobile.component.scss']
})

export class ClientsListComponent extends TablePage<Client> implements OnInit {
  public displayedColumns: string[] = ['index', 'firstName', 'lastName', 'email', 'phone', 'records', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Client> | undefined;

  constructor(dialog: MatDialog,
              customDialog: MatDialog,
              public confirmationService: ConfirmationService,
              private clientService: ClientService,
              private _snackBar: MatSnackBar,
              private notificationService: NotificationService,
              private deviceService: DeviceDetectorService,
              private router: Router,
              private route: ActivatedRoute) {
    super(dialog);
    this.isMobile = this.deviceService.isMobile();
    this.customDialog = customDialog;
  }

  isMobile: boolean = false;

  customDialog: MatDialog;

  ngOnInit() {
    this.loadData();
  }

  addNewClient() {
    this.openDialog(new Client());
  }

  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.clientService.getAllClients(searchText, page));
  }

  getMatPaginator(): MatPaginator | undefined {
    return this.paginator;
  }

  getMatTable(): MatTable<Client> | undefined {
    return this.table;
  }

  editClient(client: Client) {
    this.openDialog(client);
  }

  onDeleteClient(client: Client) {
    this.confirmationService.show(() => this.deleteClient(client.id!));
  }

  private deleteClient = (id: string) => {
    this.clientService.deleteClient(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@client-delete-success:The client has been successfully deleted`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't Delete`, err.error.error)
    })
  }

  private openDialog(client: Client) {
    this.createDialog(AddClientComponent, client,
      data => this.clientService.createClient(data),
      (id, data) => this.clientService.updateClient(id, data),
      data => !data.id
        ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@client-create-success:A new client has been added to your workplace`)
        : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@client-update-success:The client has been updated`),
      (data, err) => !data.id
        ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't Create`, err.error.error)
        : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't Update`, err.error.error),
      this.isMobile ? "100vw" : ""
    );
  }

  public openClientRecords(client: Client) {
    this.router.navigate(["../" + client.id], {relativeTo: this.route});
  }
}
