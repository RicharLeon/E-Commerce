import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Category } from '../../../models/category';
import { Product } from '../../../models/product';
import { RangePipe } from '../../../pipes/range.pipe';
import { CategoryService } from '../../service/category.service';
import { ProductService } from '../../service/products.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RangePipe],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent implements OnInit {

  products: Product[] = [];
  categorys: Category[] = [];
  filteredProducts: Product[] = [];
  filteredCategory: Category[] = [];
  loading = false;
  error = false;
  errorMessage = '';
  searchQuery = '';
  selectedCategory = '';
  currentPage = 1;
  itemsPerPage = 10;


  constructor(private productService: ProductService, 
    private categoryService: CategoryService,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.loadCategory();
  }

  loadCategory(): void {
    this.loading = true;
    this.categoryService.getProducts().subscribe({
      next: (data) => {
        this.categorys = data;
        this.filteredCategory = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = true;
        this.errorMessage = error.message;
        this.loading = false;
      }
    });
  }


  get paginatedCategory(): Category[] {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredCategory.slice(start, start + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredCategory.length / this.itemsPerPage);
  }

  applyFilters(): void {
    this.filteredCategory = this.categorys.filter(product => {
      const matchesSearch = product.name.toLowerCase().includes(this.searchQuery.toLowerCase());
  
      return matchesSearch;
    });
    this.currentPage = 1;
  }

  editCategory(categoryId: number) {
    // Navega a la ruta de edición, por ejemplo: /edit-category/:id
    this.router.navigate(['/edit-category', categoryId]);
  }

  deleteCategory(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta categoría?')) {
      this.categoryService.deleteCategory(id).subscribe({
        next: () => this.loadCategory(),
        error: (error) => console.error('Error deleting category', error)
      });
    }
  }

}
