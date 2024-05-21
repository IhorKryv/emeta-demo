import {Client} from "../../clients/model/client";

export class Session {
  id: string | undefined;
  workplaceId: string | undefined;
  name: string | undefined;
  description: string | undefined;
  createdAt: Date | undefined;
  updatedAt: Date | undefined;
  startTime: Date | undefined;
  endTime: Date | undefined;
  sessionDuration: number | undefined;
  inProgress: boolean | undefined;
  expectedEndTime: Date | undefined;
  hostFullName: string | undefined;
  sessionStatus: SessionStatus | undefined;
  url: string | undefined;
  clients: Client[] = [];
  instant: boolean = false;


}

export enum SessionStatus {
  NEW = "NEW", IN_PROGRESS = "IN_PROGRESS", FINISHED = "FINISHED", ARCHIVED = "ARCHIVED", MISSED = "MISSED"
}
