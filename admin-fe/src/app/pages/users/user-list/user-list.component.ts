import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {TablePage} from "../../../../common/components/page-template/table-page";
import {User} from "../model/user";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {UserService} from "../service/user.service";
import {MatDialog} from "@angular/material/dialog";
import {AddUserComponent} from "../add-user-dialog/add-user.component";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {MatSnackBar} from "@angular/material/snack-bar";
import {NotificationService} from "../../../../common/components/notification/notification.service";

@Component({
  selector: 'users',
  templateUrl: 'user-list.component.html',
  styleUrls: ['user-list.component.html']
})

export class UserListComponent extends TablePage<User> implements OnInit {
  public displayedColumns: string[] = ['index', 'username', 'firstName', 'lastName', 'roles', 'createdAt', 'actions'];

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<User> | undefined;

  constructor(dialog: MatDialog,
              public confirmationService: ConfirmationService,
              private userService: UserService,
              private _snackBar: MatSnackBar,
              private notificationService: NotificationService) {
    super(dialog);
  }

  ngOnInit() {
    this.loadData();
  }

  addNewUser() {
    this.openDialog(new User());
  }

  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.userService.getAllUsers(searchText, page));
  }

  getMatPaginator(): MatPaginator | undefined {
    return this.paginator;
  }

  getMatTable(): MatTable<User> | undefined {
    return this.table;
  }

  editUser(user: User) {
    this.openDialog(user);
  }

  onDeleteUser(user: User) {
    this.confirmationService.show(() => this.deleteUser(user.id!));
  }

  private deleteUser = (id: string) => {
    this.userService.deleteUser(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`, $localize`:@@user-delete-success:The user has been successfully deleted`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't delete`, err.error.error)
    })
  }

  private openDialog(user: User) {
    this.createDialog(AddUserComponent, user,
      data => this.userService.createUser(data),
      (id, data) => this.userService.updateUser(id, data),
      data => !data.id
        ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@user-create-success:A new user has been created`)
        : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@user-update-success:The user has been updated`),
      (data, err) => !data.id
        ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't create`, err.error.error)
        : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`, err.error.error)
    );
  }
}
