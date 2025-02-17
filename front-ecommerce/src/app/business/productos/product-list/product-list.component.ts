import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { Product } from '../../../models/product';
import { ProductService } from '../../../admin/service/products.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Page } from '../../../models/page';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';



@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {
buyProduct(arg0: number) {
throw new Error('Method not implemented.');
}

  productos: Product[] = [];

  filterForm: FormGroup;
  productsPage!: Page<Product>;
  pageSize: number = 10;
  currentPage: number = 0;


  constructor(private router: Router, private productService: ProductService,
    private sanitizer: DomSanitizer, private fb: FormBuilder,
  ) {
    this.filterForm = this.fb.group({
      categoryId: [''],
      minPrice: [''],
      maxPrice: [''],
      minStock: [''],
      status: [''],
      nameCategory: ['']
    });
   }

  onProductClick(id: number) {
    this.router.navigate(['/product-overviews/' + id]);
    console.log(id);
  }

  ngOnInit(): void {
    this.loadProducts();
   
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.productos = data;
        console.log(this.productos);
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  getSafeImageUrl(photo: string): SafeUrl {
    // Asegúrate de ajustar el tipo MIME según corresponda (image/png, image/jpeg, etc.)
    const imageUrl = 'data:image/png;base64,' + photo;
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
  }

 


}
