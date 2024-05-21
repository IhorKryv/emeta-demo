import {Pipe, PipeTransform} from '@angular/core';
import {Category} from "../categories/model/category";

@Pipe({
  name: 'categoriesListPipe'
})

export class CategoriesListPipe implements PipeTransform {
  transform(value: Category[], ...args: any[]): string[] {
      return value.map(category => category.name!);
  }
}
