import {Injectable} from '@angular/core';
import {HttpService} from "../../../core/http/http.service";
import {PageObject} from "../../../../common/components/pagination/page-object";
import {Client} from "../model/client";
import ApiBuilderUtils from "../../../core/api/api-builder.service";
import {ClientRecord} from "../model/client-record";

@Injectable()
export class ClientRecordsService {

  constructor(private http: HttpService) {
  }

  instance: string = "client-record";

  createRecord = (clientId: string, record: ClientRecord) => {
    record.clientId = clientId;
    return this.http.doPost<ClientRecord>(ApiBuilderUtils.buildAPI(this.instance, [clientId, "create"]), record);
  }

  addFilesToRecord = (recordId: string, formData: any) => {
    return this.http.doPost<void>(ApiBuilderUtils.buildAPI(this.instance, [recordId, "files/add"]), formData);

  }

  updateRecord = (recordId: string, record: ClientRecord) => {
    return this.http.doPut<ClientRecord>(ApiBuilderUtils.buildAPI(this.instance, [recordId, "update"]), record);
  }

  getAllRecords = (clientId: string) => {
    return this.http.doGet<ClientRecord[]>(ApiBuilderUtils.buildAPI(this.instance, [clientId, "all"]));
  }

  removeClientRecord = (clientId: string, recordId: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, [clientId, "remove", recordId]));
  }

  removeFile = (clientId: string, fileId: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, [clientId, "remove/file", fileId]));
  }
}
