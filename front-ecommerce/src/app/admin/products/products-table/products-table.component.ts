import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../service/products.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Product } from '../../../models/product';
import { RangePipe } from '../../../pipes/range.pipe';
import { Category } from '../../../models/category';
import { CategoryService } from '../../service/category.service';


@Component({
  selector: 'app-products-table',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RangePipe],
  templateUrl: './products-table.component.html',
  styleUrl: './products-table.component.css'
})
export class ProductsTableComponent implements OnInit {

  products: Product[] = [];
  categorys: Category[] = [];
  filteredProducts: Product[] = [];
  loading = false;
  error = false;
  errorMessage = '';
  searchQuery = '';
  selectedCategory = '';
  currentPage = 1;
  itemsPerPage = 10;

  constructor(private productService: ProductService, 
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.loadProducts();
    this.loadCategory();
  }

  loadProducts(): void {
    this.loading = true;
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.filteredProducts = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = true;
        this.errorMessage = error.message;
        this.loading = false;
      }
    });
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
    this.filteredProducts = this.products.filter(product => {
      const matchesSearch = product.name.toLowerCase().includes(this.searchQuery.toLowerCase());
      const matchesCategory = this.selectedCategory ? 
        product.idCategory === +this.selectedCategory : 
        true;
      return matchesSearch && matchesCategory;
    });
    this.currentPage = 1;
  }

  deleteProduct(id: number): void {
    if (confirm('¿Estás seguro de eliminar este producto?')) {
      this.productService.deleteProduct(id).subscribe({
        next: () => this.loadProducts(),
        error: (error) => console.error('Error deleting product', error)
      });
    }
  }

}
