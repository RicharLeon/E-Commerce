import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoryService } from '../../service/category.service';
import { Category } from '../../../models/category';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-category-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './category-edit.component.html',
  styleUrl: './category-edit.component.css'
})
export class CategoryEditComponent implements OnInit {
  categoryForm: FormGroup;
  categoryId!: number;
  loading: boolean = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private categoryService: CategoryService
  ) {
    // Define el formulario con los mismos controles que en la interfaz Category
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      status: [true, Validators.required]  // status como booleano
    });
  }

  ngOnInit(): void {
    // Obtén el id de la categoría desde la URL
    this.categoryId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.categoryId) {
      this.getCategory(this.categoryId);
    }
  }

  getCategory(id: number) {
    this.loading = true;
    this.categoryService.getProductById(id).subscribe({
      next: (category: Category) => {
        // Rellena el formulario con los datos obtenidos
        this.categoryForm.patchValue({
          name: category.name,
          description: category.description,
          status: category.status
        });
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar la categoría';
        this.loading = false;
      }
    });
  }

  onSubmit() {
    if (this.categoryForm.valid) {
      const updatedCategory: Category = {
        id: this.categoryId,
        name: this.categoryForm.value.name,
        description: this.categoryForm.value.description,
        status: this.categoryForm.value.status
      };

      this.categoryService.updateProduct(updatedCategory).subscribe({
        next: () => {
          // Redirecciona o muestra mensaje de éxito
          this.router.navigate(['/categoria-list']);
        },
        error: (err) => {
          this.error = 'Error al actualizar la categoría';
        }
      });
    }
  }

  goBack() {
    this.router.navigate(['/categories']);
  }
}
