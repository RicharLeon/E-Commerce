import { Component } from '@angular/core';
import { FooterComponent } from "../../shared/footer/footer.component";
import { ProductosComponent } from "../productos/productos/productos.component";
import { ProductListComponent } from "../productos/product-list/product-list.component";
import { ProductFilterComponent } from "../productos/product-filter/product-filter.component";

@Component({
  selector: 'app-principal',
  standalone: true,
  imports: [FooterComponent, ProductosComponent, ProductListComponent, ProductFilterComponent],
  templateUrl: './principal.component.html',
  styleUrl: './principal.component.css'
})
export class PrincipalComponent {

}
