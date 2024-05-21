import {Workplace} from "./workplace";

export class WorkplaceDetails extends Workplace {
  expiredAt: Date | undefined;
  planPaidUntil: Date | undefined;
}
