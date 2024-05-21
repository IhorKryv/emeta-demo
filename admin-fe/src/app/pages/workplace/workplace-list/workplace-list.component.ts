import { Component, OnInit } from '@angular/core';
import {TablePage} from "../../../../common/components/page-template/table-page";
import {Workplace} from "../model/workplace";
import {MatDialog} from "@angular/material/dialog";
import {MatPaginator} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {WorkplaceService} from "../service/workplace.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'workplace-list',
  templateUrl: './workplace-list.component.html',
  styleUrls: ['./workplace-list.component.scss']
})
export class WorkplaceListComponent extends TablePage<Workplace> implements OnInit {

  public displayedColumns: string[] = ['index', 'firstName', 'lastName', 'email', 'createdDate', 'plan', 'status', 'actions'];

  constructor(dialog: MatDialog, private workplaceService: WorkplaceService, private router: Router, private route: ActivatedRoute) {
    super(dialog);
  }

  ngOnInit(): void {
    this.loadData();
  }

  addNewWorkplace() {
    this.router.navigate(['new'], {relativeTo: this.route});
  }

  editWorkplace(workplace: Workplace) {
    this.router.navigate([workplace.id], {relativeTo: this.route});
  }


  getMatPaginator(): MatPaginator | undefined {
    return undefined;
  }

  getMatTable(): MatTable<Workplace> | undefined {
    return undefined;
  }

  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.workplaceService.getWorkspaces(searchText, page));
  }

}
