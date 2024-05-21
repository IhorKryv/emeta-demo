import {Injectable} from '@angular/core';
import {HttpService} from "../../core/http/http.service";
import ApiBuilderUtils from "../../core/api/api-builder.service";
import {PageObject} from "../../../common/components/pagination/page-object";
import {Preference} from "./model/preference";
import {Plan} from "./model/plan";
import {PlanDetails} from "./model/plan-details";

@Injectable()
export class PaymentPlanService {

  private itemsInstance: string = "plan-preference";
  private plansInstance: string = "plan";

  constructor(private httpService: HttpService) {
  }

  public getItems(searchText: string = "", page: number = 0) {
    return this.httpService.doGet<PageObject<Preference>>(ApiBuilderUtils.buildAPI(this.itemsInstance, [], ApiBuilderUtils.getDefaultParams(searchText, page)));
  }

  public getItemsList() {
    return this.httpService.doGet<Preference[]>(ApiBuilderUtils.buildAPI(this.itemsInstance, ["list"]));
  }

  public addItem(item: Preference) {
    return this.httpService.doPost<Preference>(ApiBuilderUtils.buildAPI(this.itemsInstance, []), item);
  }

  public updateItem(id: string, item: Preference) {
    return this.httpService.doPut<Preference>(ApiBuilderUtils.buildAPI(this.itemsInstance, [id]), item);
  }

  public deleteItem(id: string) {
    return this.httpService.doDelete<Preference>(ApiBuilderUtils.buildAPI(this.itemsInstance, [id]));
  }

  public forceDeleteItem(id: string) {
    return this.httpService.doDelete<Preference>(ApiBuilderUtils.buildAPI(this.itemsInstance, [id, "force"]));
  }

  public getPlans(searchText: string = "", page: number = 0) {
    return this.httpService.doGet<PageObject<Plan>>(ApiBuilderUtils.buildAPI(this.plansInstance, [], ApiBuilderUtils.getDefaultParams(searchText, page)));
  }

  public getPlan(id: string) {
    return this.httpService.doGet<PlanDetails>(ApiBuilderUtils.buildAPI(this.plansInstance, [id]));
  }

  public addPlan(plan: Plan) {
    return this.httpService.doPost<Plan>(ApiBuilderUtils.buildAPI(this.plansInstance, []), plan);
  }

  public makePlanDefault(id: string) {
    return this.httpService.doGet<Plan>(ApiBuilderUtils.buildAPI(this.plansInstance, [id, "set-default"]));
  }

  public updatePlan(id: string, plan: Plan) {
    return this.httpService.doPut<Plan>(ApiBuilderUtils.buildAPI(this.plansInstance, [id]), plan);
  }

  public deletePlan(id: string) {
    return this.httpService.doDelete<Plan>(ApiBuilderUtils.buildAPI(this.plansInstance, [id]));
  }
}
