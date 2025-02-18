import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { Product } from '../../../models/product';
import { ProductService } from '../../../admin/service/products.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Page } from '../../../models/page';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Category } from '../../../models/category';
import { CategoryService } from '../../../admin/service/category.service';
import { RangePipe } from '../../../pipes/range.pipe';



@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RangePipe],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {
  buyProduct(arg0: number) {
    throw new Error('Method not implemented.');
  }

  productos: Product[] = [];
  categorys: Category[] = [];
  filteredProducts: Product[] = [];
  loading = false;
  error = false;
  errorMessage = '';
  searchQuery = '';
  selectedCategory = '';
  currentPage = 1;
  itemsPerPage = 10;

  productsPage!: Page<Product>;
  pageSize: number = 10;


  constructor(private router: Router, private productService: ProductService,
    private sanitizer: DomSanitizer, private fb: FormBuilder,
    private categoryService: CategoryService
  ) {

  }

  onProductClick(id: number) {
    this.router.navigate(['/product-overviews/' + id]);
    console.log(id);
  }

  ngOnInit(): void {
    this.loadCategory();
    this.loadProducts();

  }

  loadProducts(): void {
    this.loading = true;
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.productos = data;
        this.filteredProducts = data;
        this.loading = false;
        
        console.log(this.productos);
      },
      error: (error) => {
        this.error = true;
        this.errorMessage = error.message;
        this.loading = false;
      }
    });
  }

  getSafeImageUrl(photo: string): SafeUrl {
    // Asegúrate de ajustar el tipo MIME según corresponda (image/png, image/jpeg, etc.)
    const imageUrl = 'data:image/png;base64,' + photo;
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
  }

  loadCategory(): void {
    this.loading = true;
    this.categoryService.getProducts().subscribe({
      next: (data) => {
        this.categorys = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = true;
        this.errorMessage = error.message;
        this.loading = false;
      }
    });
  }

  get paginatedProducts(): Product[] {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredProducts.slice(start, start + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredProducts.length / this.itemsPerPage);
  }

  applyFilters(): void {
    this.filteredProducts = this.productos.filter(product => {
      const matchesSearch = product.name.toLowerCase().includes(this.searchQuery.toLowerCase());
      const matchesCategory = this.selectedCategory ? 
        product.idCategory === +this.selectedCategory : 
        true;
      return matchesSearch && matchesCategory;
    });
    this.currentPage = 1;
  }


}
