import {Injectable} from '@angular/core';
import {HttpService} from "../../../core/http/http.service";
import {PageObject} from "../../../../common/components/pagination/page-object";
import ApiBuilderUtils from "../../../core/api/api-builder.service";
import {Workplace} from "../model/workplace";
import {PlanInfo} from "../../payment-plan/model/plan-info";
import {Observable} from "rxjs";
import {PlanDetails} from "../../payment-plan/model/plan-details";
import {WorkplaceDetails} from "../model/workplace-details";

@Injectable()
export class WorkplaceService {

  private workplacesInstance: string = "workplace";
  private plansInstance: string = "plan";

  constructor(private httpService: HttpService) {
  }

  public getWorkspaces(searchText: string = "", page: number = 0) {
    return this.httpService.doGet<PageObject<Workplace>>(ApiBuilderUtils.buildAPI(this.workplacesInstance, [], ApiBuilderUtils.getDefaultParams(searchText, page)));
  }

  public getWorkspace(id: string) {
    return this.httpService.doGet<WorkplaceDetails>(ApiBuilderUtils.buildAPI(this.workplacesInstance, [id]));
  }

  public getPlans(params?:{searchText?: string, page?: number, size?: number}): Observable<PageObject<PlanInfo>> {
    return this.httpService.doGet<PageObject<PlanInfo>>(ApiBuilderUtils.buildAPI(this.plansInstance, [], ApiBuilderUtils.getDefaultParams(params?.searchText || "", params?.page || 0, params?.size || 10)));
  }

  public getPlan(id:string) {
    return this.httpService.doGet<PlanDetails>(ApiBuilderUtils.buildAPI(this.plansInstance, [id]));
  }

  public createWorkplace(workplace: WorkplaceDetails) {
    return this.httpService.doPost<WorkplaceDetails>(ApiBuilderUtils.buildAPI(this.workplacesInstance, []), workplace);
  }

  public updateWorkplaceData(id:string , workplace: WorkplaceDetails) {
    return this.httpService.doPut<WorkplaceDetails>(ApiBuilderUtils.buildAPI(this.workplacesInstance, [id]), workplace);
  }

  public updateWorkplacePlan(id:string , planId: string) {
    return this.httpService.doPut<WorkplaceDetails>(ApiBuilderUtils.buildAPI(this.workplacesInstance, [id, "plan"]), {id: planId});
  }

  public updateWorkplaceStatus(id:string , status: string) {
    return this.httpService.doPut<WorkplaceDetails>(ApiBuilderUtils.buildAPI(this.workplacesInstance, [id, "status"]), {status: status});
  }
}
