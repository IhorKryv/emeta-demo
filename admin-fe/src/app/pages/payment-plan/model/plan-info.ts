import {AutocompleteItem} from "../../../../common/components/autocomplete/autocomplete-item";

export class PlanInfo implements AutocompleteItem {
  id: string | undefined;
  name: string | undefined;
  price: number | undefined;
  duration: string | undefined;

  getLabel(): string {
    return this.name || '';
  }
}
