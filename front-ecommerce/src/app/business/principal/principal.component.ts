import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../shared/footer/footer.component";
import { ProductosComponent } from "../productos/productos/productos.component";
import { ProductListComponent } from "../productos/product-list/product-list.component";
import { ProductFilterComponent } from "../productos/product-filter/product-filter.component";
import { Product } from '../../models/product';
import { ProductService } from '../../admin/service/products.service';

import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-principal',
  standalone: true,
  imports: [ ProductFilterComponent, CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './principal.component.html',
  styleUrl: './principal.component.css'
})
export class PrincipalComponent {
  
}
