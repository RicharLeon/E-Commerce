import { Component } from '@angular/core';
import { ProductListComponent } from "../product-list/product-list.component";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-product-filter',
  standalone: true,
  imports: [ProductListComponent, FontAwesomeModule],
  templateUrl: './product-filter.component.html',
  styleUrl: './product-filter.component.css'
})
export class ProductFilterComponent {

}
