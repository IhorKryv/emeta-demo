import {PlanInfo} from "./plan-info";

export class Plan extends PlanInfo {
  description: string | undefined;
  enabled: boolean | undefined;
  defaultPlan: boolean | undefined;
}
