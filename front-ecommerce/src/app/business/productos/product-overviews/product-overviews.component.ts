import { Component } from '@angular/core';
import { CategoryListComponent } from '../../../admin/categorys/category-list/category-list.component';
import { ProductsCreateComponent } from "../../../admin/products/products-create/products-create.component";

@Component({
  selector: 'app-product-overviews',
  standalone: true,
  imports: [CategoryListComponent, ProductsCreateComponent],
  templateUrl: './product-overviews.component.html',
  styleUrl: './product-overviews.component.css'
})
export class ProductOverviewsComponent {

}
