import {Component, OnInit, ViewChild} from '@angular/core';
import {TablePage} from "../../../../common/components/page-template/table-page";
import {Category} from "./model/category";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmationService} from "../../../../common/components/confirmation/confirmation.service";
import {NotificationService} from "../../../../common/components/notification/notification.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {Role} from "../../security/roles/model/role";
import {CategoryService} from "./service/category.service";
import {AddRolesDialogComponent} from "../../security/add-roles-dialog/add-roles.component";
import {NotificationType} from "../../../../common/components/notification/notification-type";
import {AddCategoryDialogComponent} from "./add-category-dialog/add-category.component";

@Component({
  selector: 'categories-component',
  templateUrl: 'categories.component.html',
  styleUrls: ['categories.component.scss']
})

export class CategoriesComponent extends TablePage<Category> implements OnInit {
  constructor(dialog: MatDialog,
              private categoryService: CategoryService,
              private _snackBar: MatSnackBar,
              public confirmationService: ConfirmationService,
              private notificationService: NotificationService) {
    super(dialog);
  }

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatTable) table: MatTable<Role> | undefined;

  public displayedColumns: string[] = ['index', 'name', 'identifier', 'description', 'actions'];
  public searchValue = "";

  ngOnInit() {
    this.loadData()
  }

  addNewCategory() {
    this.openDialog(new Category());
  }

  editCategory(category: Category) {
    this.openDialog(category);
  }

  onCategoryDelete(category: Category) {
    this.confirmationService.show(() => this.deleteCategory(category.id!));
  }

  private deleteCategory = (id: string) => {
    this.categoryService.deleteCategory(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`,  $localize`:@@category-delete-success:The category has been successfully deleted`)
        this.loadData();
      },
      error: err => {
        console.log(err);
        this.confirmationService.show(() => this.deleteCategoriesAndAttachments(id));
      }
    })
  }

  private deleteCategoriesAndAttachments(id: string) {
    this.categoryService.deleteCategoryWithAttachments(id).subscribe({
      next: () => {
        this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@delete-success:Successfully deleted`,  $localize`:@@category-and-attachments-delete-success:The category and attachments have been successfully deleted`)
        this.loadData();
      },
      error: err => this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@delete-error:Can't delete`, err.error.error)
    })
  }


  getMatPaginator(): MatPaginator | undefined {
    return undefined;
  }

  getMatTable(): MatTable<Category> | undefined {
    return undefined;
  }

  loadData(searchText: string = "", page: number = 0): void {
    this.loadTable(() => this.categoryService.getAllCategories(searchText, page));
  }

  private openDialog(category: Category) {
    this.createDialog(AddCategoryDialogComponent, category,
      data => this.categoryService.createCategory(data),
      (id, data) => this.categoryService.updateCategory(id, data),
      data => {
        this.loadData("", this.pagination.page);
        !data.id
          ? this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@create-success:Successfully created`, $localize`:@@category-create-success:A new category has been added to app`)
          : this.notificationService.showNotification(NotificationType.SUCCESS, $localize`:@@update-success:Successfully updated`, $localize`:@@category-update-success:The category has been updated`)
      },
      (data, err) => {
        this.loadData("", this.pagination.page);
        !data.id
          ? this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@create-error:Can't create`, err.error.error)
          : this.notificationService.showNotification(NotificationType.ERROR, $localize`:@@update-error:Can't update`, err.error.error)
      },
    );
  }
}
