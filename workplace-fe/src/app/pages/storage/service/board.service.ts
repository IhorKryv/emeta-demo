import {Injectable} from '@angular/core';
import ApiBuilderUtils from "../../../core/api/api-builder.service";
import {PageObject} from "../../../../common/components/pagination/page-object";
import {HttpService} from "../../../core/http/http.service";
import {Board} from "../model/board";
import {Deck} from "../model/deck";

@Injectable()
export class BoardService {

  constructor(private http: HttpService) {
  }

  instance = "session-board";
  adminInstance = "admin-board"

  createBoard = (board: Board) => {
    return this.http.doPost<Board>(ApiBuilderUtils.buildAPI(this.instance, ["create"]), board);
  }

  addAdminBoard = (board: Board) => {
    return this.http.doPost<void>(ApiBuilderUtils.buildAPI(this.adminInstance, ["add"]), board);
  }

  uploadBoardBackgroundImage = (id: string, formData: any) => {
    return this.http.doPut<Board>(ApiBuilderUtils.buildAPI(this.instance, [id, "upload"]), formData);
  }

  updateBoard = (id: string, board: Board) => {
    return this.http.doPut<Board>(ApiBuilderUtils.buildAPI(this.instance, ["update", id]), board);
  }

  updateAdminBoard = (id: string, name: string, description: string) => {
    return this.http.doPut<Board>(ApiBuilderUtils.buildAPI(this.adminInstance, ["update", id]), {name: name, description: description});
  }

  getAllBoards = (searchText: string, page: number) => {
    return this.http.doGet<PageObject<Board>>(ApiBuilderUtils.buildAPI(this.instance, ["get/all"], ApiBuilderUtils.getDefaultParams(searchText, page)));
  }

  getAllBoardsFromAdmin = () => {
    return this.http.doGet<Board[]>(ApiBuilderUtils.buildAPI(this.adminInstance, ["get/all"]));
  }

  getAllSelectedAdminBoards = () => {
    return this.http.doGet<Board[]>(ApiBuilderUtils.buildAPI(this.adminInstance, ["get/selected"]));
  }

  getAdminBoardStats = () => {
    return this.http.doGet<any>(ApiBuilderUtils.buildAPI(this.adminInstance, ["get/stats"]));
  }

  deleteBoard = (id: string) => {
    return this.http.doDelete<void>(ApiBuilderUtils.buildAPI(this.instance, ["delete", id]));
  }

}
