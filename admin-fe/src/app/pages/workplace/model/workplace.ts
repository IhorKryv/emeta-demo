import {PlanInfo} from "../../payment-plan/model/plan-info";
import {AutocompleteItem} from "../../../../common/components/autocomplete/autocomplete-item";

export class Workplace implements AutocompleteItem {
  id: string | undefined;
  firstName: string | undefined;
  lastName: string | undefined;
  email: string | undefined;
  phone: string | undefined;
  plan: PlanInfo | undefined;
  status: string | undefined;
  createdDate: Date = new Date();
  getLabel(): string {
    return this.firstName || ' ' + ' ' + this.lastName || ' ';
  }
}
