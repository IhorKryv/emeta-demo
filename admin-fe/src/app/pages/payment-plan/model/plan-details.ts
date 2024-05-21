import {Preference} from "./preference";
import {Plan} from "./plan";
import {MacSettings} from "./mac-settings";

export class PlanDetails extends Plan {
  macSettings: MacSettings = new MacSettings();
  preferences: {id: string | undefined }[] | Preference[] | undefined = [];
}
