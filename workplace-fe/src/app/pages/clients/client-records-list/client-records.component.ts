import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Client} from "../model/client";
import {ClientFile, ClientRecord} from "../model/client-record";
import {ClientRecordsService} from "../service/client-record.service";
import {ActivatedRoute} from "@angular/router";
import {AddClientRecordComponent} from "../add-client-record-dialog/add-client-record.component";
import {FileHandle} from "../../../../common/components/dragDrop/dragDrop.directive";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {ClientService} from "../service/client.service";
import {C} from "@angular/cdk/keycodes";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'client-records',
  templateUrl: 'client-records.component.html',
  styleUrls: ['client-records.component.scss', 'client-records-mobile.component.scss']
})

export class ClientRecordsComponent implements OnInit {
  constructor(addDialog: MatDialog,
              private clientRecordsService: ClientRecordsService,
              private activeRouter: ActivatedRoute,
              private clientService: ClientService,
              private confirmationService: ConfirmationService,
              private deviceService: DeviceDetectorService) {
    this.addDialog = addDialog;
    this.isMobile = this.deviceService.isMobile();
  }

  isMobile: boolean = false;
  records: ClientRecord[] = [];
  addDialog: MatDialog;
  clientId: string | undefined;
  client: Client = new Client();
  ngOnInit() {
    let id = this.activeRouter.snapshot.paramMap.get('id');
    if (id) {
      this.clientId = id;
      this.clientService.getSingleClient(this.clientId).subscribe({
        next: resp => this.client = resp
      });
      this.loadRecords();
    }
  }

  onDeleteClientRecord(record: ClientRecord) {
    this.confirmationService.show(() => this.removeRecord(record));
  }

  onDeleteClientFile(file: ClientFile) {
    this.confirmationService.show(() => this.removeFile(file));
  }

  loadRecords = () => {
    this.clientRecordsService.getAllRecords(this.clientId!).subscribe({
      next: resp => {
        this.records = [...resp];
      },
      error: err => console.log(err)
    });
  }

  public addRecord(clientRecord: ClientRecord | null) {
    this.addDialog.open(AddClientRecordComponent, {
      width: '100vw',
      data: {clientId: this.clientId, record: clientRecord}
    }).afterClosed().subscribe({
      next: clientRecord => {
        if (clientRecord) {
          if (clientRecord.id) {
            this.clientRecordsService.updateRecord(clientRecord.id, clientRecord).subscribe({
              next: () => this.loadRecords()
            })
          } else {
            this.clientRecordsService.createRecord(this.clientId!, clientRecord).subscribe({
              next: () => this.loadRecords()
            })
          }
        }
      }
    });
  }

  removeRecord = (record: ClientRecord) => {
    this.clientRecordsService.removeClientRecord(this.clientId!, record.id!).subscribe({
      next: () => this.loadRecords()
    })
  }

  removeFile = (file: ClientFile) => {
    this.clientRecordsService.removeFile(this.clientId!, file.id!).subscribe({
      next: () => this.loadRecords()
    })
  }

  uploadFiles = (record: ClientRecord, files: FileHandle[]) => {
    const formData = new FormData();
    for (let fileHandle of files) {
      formData.append("files", fileHandle.file);
    }
    this.clientRecordsService.addFilesToRecord(record.id!, formData).subscribe({
      next: () => {
        this.loadRecords();
      }
    })
  }

  uploadFilesMobile = (record: ClientRecord, event: any) => {
    const formData = new FormData();
    for (let file of event.target.files) {
      formData.append("files", file);
    }
    this.clientRecordsService.addFilesToRecord(record.id!, formData).subscribe({
      next: () => {
        this.loadRecords();
      }
    })
  }
}
