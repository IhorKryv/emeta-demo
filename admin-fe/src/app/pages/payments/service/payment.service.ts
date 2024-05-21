import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {PageObject} from "../../../../common/components/pagination/page-object";
import ApiBuilderUtils from "../../../core/api/api-builder.service";
import {HttpService} from "../../../core/http/http.service";
import {Workplace} from "../../workplace/model/workplace";
import {Plan} from "../../payment-plan/model/plan";
import {PlanDetails} from "../../payment-plan/model/plan-details";
import {Payment} from "../model/payment";

@Injectable()
export class PaymentService {

  private workplacesInstance: string = "workplace";
  private paymentsInstance: string = "payments";

  constructor(private httpService: HttpService) {
  }

  public getWorkplaces(params?:{searchText?: string, page?: number, size?: number}): Observable<PageObject<Workplace>> {
    return this.httpService.doGet<PageObject<Workplace>>(ApiBuilderUtils.buildAPI(this.workplacesInstance, [], ApiBuilderUtils.getDefaultParams(params?.searchText || "", params?.page || 0, params?.size || 10)));
  }

  public getPayments(searchText: string = "", page: number = 0) {
    return this.httpService.doGet<PageObject<Payment>>(ApiBuilderUtils.buildAPI(this.paymentsInstance, [], ApiBuilderUtils.getDefaultParams(searchText, page)));
  }

  public addPayment(plan: Payment) {
    return this.httpService.doPost<Payment>(ApiBuilderUtils.buildAPI(this.paymentsInstance, []), plan);
  }

  public updatePayment(id: string, plan: Payment) {
    return this.httpService.doPut<Payment>(ApiBuilderUtils.buildAPI(this.paymentsInstance, [id]), plan);
  }

  public deletePlan(id: string) {
    return this.httpService.doDelete<void>(ApiBuilderUtils.buildAPI(this.paymentsInstance, [id]));
  }
}
