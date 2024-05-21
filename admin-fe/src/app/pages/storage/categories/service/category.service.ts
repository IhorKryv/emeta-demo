import {Injectable} from '@angular/core';
import {HttpService} from "../../../../core/http/http.service";
import {Category} from "../model/category";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";
import {PageObject} from "../../../../../common/components/pagination/page-object";

@Injectable()
export class CategoryService {

  constructor(private http: HttpService) {
  }

  instance = "category";

  createCategory = (category: Category) => {
    return this.http.doPost<Category>(ApiBuilderUtils.buildAPI(this.instance, ["create"]), category);
  }

  updateCategory = (id: string, category: Category) => {
    return this.http.doPut<Category>(ApiBuilderUtils.buildAPI(this.instance, ["update", id]), category);
  }

  getSingleCategory = (id: string) => {
    return this.http.doGet<Category>(ApiBuilderUtils.buildAPI(this.instance, ["get", id]));
  }

  getAllCategoriesByNames = (names: string[]) => {
    return this.http.doPost<Category[]>(ApiBuilderUtils.buildAPI(this.instance, ["get/all/names"]), names);
  }

  getAllCategories = (searchText: string = "", page: number = 0) => {
    return this.http.doGet<PageObject<Category>>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"], ApiBuilderUtils.getDefaultParams(searchText, page)));
  }

  getAllCategoriesForSelection = () => {
    return this.http.doGet<Category[]>(ApiBuilderUtils.buildAPI(this.instance, ["get/selection"]));
  }

  deleteCategory = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id]));
  }

  deleteCategoryWithAttachments = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id, "attachments"]));
  }
}
