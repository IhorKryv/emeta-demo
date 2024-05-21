import {Injectable} from '@angular/core';
import {HttpService} from "../../../core/http/http.service";
import {Session, SessionStatus} from "../model/session";
import ApiBuilderUtils from "../../../core/api/api-builder.service";
import {Client} from "../../clients/model/client";
import {PageObject} from "../../../../common/components/pagination/page-object";
import {SessionCalendarItem} from "../model/session-calendar-item";

@Injectable()
export class SessionsService {

  constructor(private http: HttpService) {}

  instance = "session";

  createSession = (session: Session) => {
    return this.http.doPost<Session>(ApiBuilderUtils.buildAPI(this.instance, ["create"]), session);
  }

  addClientsToSessions = (id: string, clients: Client[]) => {
    return this.http.doPost<Session>(ApiBuilderUtils.buildAPI(this.instance, [id, "clients/add"]), clients);
  }

  updateSession = (id: string, session: Session) => {
    return this.http.doPut<Session>(ApiBuilderUtils.buildAPI(this.instance, ["update", id]), session);
  }

  getSession = (id: string) => {
    return this.http.doGet<Session>(ApiBuilderUtils.buildAPI(this.instance, ["get", id]));
  }

  getAllSessionsForCalendar = () => {
    return this.http.doGet<SessionCalendarItem[]>(ApiBuilderUtils.buildAPI(this.instance, ["get/calendar"]));
  }

  getAvailableSessions = (searchText: string, size: number) => {
    return this.http.doGet<PageObject<Session>>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"], ApiBuilderUtils.getDefaultParams(searchText, size)));
  }

  getArchivedSessions = (searchText: string, size: number) => {
    return this.http.doGet<PageObject<Session>>(ApiBuilderUtils.buildAPI(this.instance, ["get/archived"], ApiBuilderUtils.getDefaultParams(searchText, size)));
  }

  startSession = (id: string) => {
    return this.http.doGet<Session>(ApiBuilderUtils.buildAPI(this.instance, ["start", id]));
  }

  stopSession = (id: string) => {
    return this.http.doGet<Session>(ApiBuilderUtils.buildAPI(this.instance, ["stop", id]));
  }

  archiveSession = (id: string) => {
    return this.http.doDelete<Session>(ApiBuilderUtils.buildAPI(this.instance, ["archive", id]));
  }

  deleteSession = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id]));
  }

  startSessionInstance = (session: Session, data: any) => {
    const sessionUrl = "https://session.emetaplus.com/create-session";
    return this.http.doPost<any>(sessionUrl, data);
  }

  updateSessionUrl = (id: string, sessionStartDto: {id:string, url: string}) => {
    return this.http.doPut<Session>(ApiBuilderUtils.buildAPI(this.instance, ["update", id, "url"]), sessionStartDto);
  }
}
