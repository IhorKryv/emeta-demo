import {Injectable} from '@angular/core';
import {HttpService} from "../../../../core/http/http.service";
import {Board} from "../model/board";
import ApiBuilderUtils from "../../../../core/api/api-builder.service";
import {CreateBoard} from "../model/create-board";

@Injectable()
export class BoardService {

  constructor(private http: HttpService) {
  }

  instance: string = "board";

  createBoard = (formData: any) => {
    return this.http.doPost<Board>(ApiBuilderUtils.buildAPI(this.instance, ["create"]), formData);
  }

  updateBoard = (id: string, formData: any) => {
    return this.http.doPut<Board>(ApiBuilderUtils.buildAPI(this.instance, ["update", id]), formData);
  }

  getBoard = (id: string) => {
    return this.http.doGet<Board>(ApiBuilderUtils.buildAPI(this.instance, ["get", id]));
  }

  getAllBoards = () => {
    return this.http.doGet<Board[]>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"]));
  }

  deleteBoard = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id]));
  }

  addImageToBoard = (id: string, file: any) => {
    return this.http.doPost<void>(ApiBuilderUtils.buildAPI(this.instance, [id, "image/add"]), file);
  }

  removeImageFromBoard = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, [id, "image/remove"]));
  }

}
