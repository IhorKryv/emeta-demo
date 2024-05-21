import {Component, OnInit, ViewChild} from '@angular/core';
import {AddRolesDialogComponent} from "../add-roles-dialog/add-roles.component";
import {MatDialog} from "@angular/material/dialog";
import {MatTable} from "@angular/material/table";
import {RoleService} from "./service/role.service";
import {Role} from "./model/role";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {TablePage} from "../../../../common/components/page-template/table-page";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'roles',
  templateUrl: 'roles.component.html',
  styleUrls: ['roles.component.scss']
})

export class RolesComponent extends TablePage<Role> implements OnInit {
  constructor(dialog: MatDialog,
              private roleService: RoleService,
              private _snackBar: MatSnackBar,
              public confirmationService: ConfirmationService,
              private notificationService: NotificationService) {
    super(dialog);
  }
  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Role> | undefined;

  public displayedColumns: string[] = ['index', 'name', 'description', 'authorities', 'actions'];
  public searchValue = "";
  public allRoles: Role[] = [];

  addNewRole() {
    this.openDialog(new Role());
  }

  editRole(role: Role) {
    this.openDialog(role);
  }

  onRoleDelete(role: Role) {
    this.confirmationService.show(() => this.deleteRole(role.id!));
  }

  ngOnInit() {
    this.loadData();
  }

  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.roleService.getAllRoles(searchText, page));
  }

  getMatPaginator(): MatPaginator | undefined {
    return this.paginator;
  }

  getMatTable(): MatTable<Role> | undefined {
    return this.table;
  }

  deleteRole = (id: string) => {
    this.roleService.deleteRole(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@role-delete-success:Role has been deleted successfully`);
        this.loadData();
      },
      error: (e) => {
        this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't delete`, e.error.error);
      }
    })
  }

  private openDialog(role: Role) {
    this.createDialog(AddRolesDialogComponent, role,
      data => this.roleService.createRole(data),
      (id, data) => this.roleService.updateRole(id, data),
      data => !data.id
        ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@role-create-success:A new role has been added to app`)
        : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@role-update-success:The role has been updated`),
      (data, err) => !data.id
        ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't create`, err.error.error)
        : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`, err.error.error)
    );
  }

}
