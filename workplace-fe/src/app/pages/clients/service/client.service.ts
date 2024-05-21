import {Injectable} from '@angular/core';
import {HttpService} from "../../../core/http/http.service";
import {Client} from "../model/client";
import ApiBuilderUtils from "../../../core/api/api-builder.service";
import {PageObject} from "../../../../common/components/pagination/page-object";

@Injectable()
export class ClientService {

  constructor(private http: HttpService) {
  }

  instance = "clients";

  createClient = (client: Client) => {
    return this.http.doPost<Client>(ApiBuilderUtils.buildAPI(this.instance, ["create"]), client);
  }

  updateClient = (id: string, client: Client) => {
    return this.http.doPut<Client>(ApiBuilderUtils.buildAPI(this.instance, ["update", id]), client);
  }

  getSingleClient = (id: string) => {
    return this.http.doGet<Client>(ApiBuilderUtils.buildAPI(this.instance, ["get", id]));
  }

  getAllClients = (searchText: string, size: number) => {
    return this.http.doGet<PageObject<Client>>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"], ApiBuilderUtils.getDefaultParams(searchText, size)));
  }

  getAllClientsForSelection = () => {
    return this.http.doGet<Client[]>(ApiBuilderUtils.buildAPI(this.instance, ["get/all/selection"]));
  }

  deleteClient = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id]));
  }
}
