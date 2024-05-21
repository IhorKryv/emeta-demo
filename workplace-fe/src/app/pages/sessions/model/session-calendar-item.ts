import {Client} from "../../clients/model/client";

export class SessionCalendarItem {
  id: string | undefined;
  name: string | undefined;
  startTime: string | undefined;
  endTime: string | undefined;
  date: Date | undefined;
  sessionStatus: string | undefined;
  duration: number | undefined;
  clients: Client[] = [];
}
