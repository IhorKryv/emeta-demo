import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Session} from "../model/session";
import {ClientService} from "../../clients/service/client.service";
import {Client} from "../../clients/model/client";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'add-client',
  templateUrl: 'add-client.component.html',
  styleUrls: ['add-client.component.scss', 'add-client-mobile.component.scss']
})

export class AddClientDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddClientDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public session: Session,
              private clientService: ClientService,
              private deviceService: DeviceDetectorService) {
    clientService.getAllClientsForSelection().subscribe({
      next: (clients) => {
        if (session.clients) {
          let sessionClientIds = session.clients.map(c => c.id);
          this.availableClients = this.isMobile ? [...clients] : clients.filter(c => !sessionClientIds.includes(c.id));
        } else {
          this.availableClients = [...clients];
        }
        console.log(this.session.clients);
      },
      error: (err) => {
        console.log(err.error.error);
      }
    });
    this.isMobile = this.deviceService.isMobile();
  }

  isMobile: boolean = false;
  availableClients: Client[] = [];

  onClose() {
    this.dialogRef.close();
  }

  onSave() {
    this.dialogRef.close(this.session);
  }

  addClient = (client: Client) => {
    let index = this.availableClients.indexOf(client);
    if (!this.isMobile) {
      this.availableClients.splice(index, 1);
    }
    this.session.clients.push(client);
  }

  isClientSelected (client: Client): boolean {
    return this.session.clients.find(c => c.id === client.id) !== undefined;
  }

  selectOrDeselectClient = (client: Client) => {
    let current =  this.session.clients.find(c => c.id === client.id);
    if (!current) {
      this.session.clients.push(client);
    } else {
      this.session.clients.splice(this.session.clients.indexOf(current), 1);
    }
    console.log(this.session.clients);
  }

  removeClient = (client: Client) => {
    let index = this.session.clients.indexOf(client);
    this.session.clients.splice(index, 1);
    this.availableClients.push(client);
  }

  hideAdditionalIcon(value: string): boolean {
    return value === '';
  }

}
