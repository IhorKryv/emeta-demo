import {Component, OnInit} from '@angular/core';
import {PermissionService} from "./service/permission.service";
import {Permission} from "./model/permission";

@Component({
  selector: 'permissions',
  templateUrl: 'permissions.component.html'
})

export class PermissionsComponent implements OnInit {
  constructor(private permissionService: PermissionService) { }


  public displayedColumns: string[] = ['position', 'name', 'description'];
  public dataSource: Permission[] = [];

  ngOnInit() {
    this.permissionService.getAllAuthorities().subscribe({
      next: (resp) => {
        this.dataSource = [...resp];
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
