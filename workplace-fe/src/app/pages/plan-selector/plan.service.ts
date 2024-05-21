import {Injectable} from '@angular/core';
import {HttpService} from "../../core/http/http.service";
import {MyPlan, Plan} from "./plan";
import ApiBuilderUtils from "../../core/api/api-builder.service";

@Injectable()
export class PlanService {

  constructor(private http: HttpService) {
  }

  instance = "plans"

  getMyPlan = () => {
    return this.http.doGet<MyPlan>(ApiBuilderUtils.buildAPI(this.instance, ["get/my"]));
  }

  getAllPlans = () => {
    return this.http.doGet<Plan[]>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"]));
  }

  subscribe = (plan: Plan) => {
    return this.http.doGet<void>(ApiBuilderUtils.buildAPI(this.instance, ["subscribe", plan.id!]))
  }
}
