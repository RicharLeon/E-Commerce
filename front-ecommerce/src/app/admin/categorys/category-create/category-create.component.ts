import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoryService } from '../../service/category.service';
import { Category } from '../../../models/category';


@Component({
  selector: 'app-category-create',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './category-create.component.html',
  styleUrl: './category-create.component.css'
})
export class CategoryCreateComponent {
  productForm: FormGroup;

  constructor(private fb: FormBuilder, private categoryService: CategoryService) {
    this.productForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      status: ['', [Validators.required, Validators.min(0)]],
    });
  }

  onSubmit() {
    if (this.productForm.valid) {


      const category: Category = {
        name: this.productForm.value.nombre,
        description: this.productForm.value.descripcion,
        status: this.productForm.value.status === 'activo' ? true : false,
      };
      console.log('Datos mapeados:', this.productForm.value);
      this.saveCategory(category);
    //  this.goBack();
    } else {
      console.log('Formulario no válido');
    }
  }


  goBack() {
    window.history.back();
  }

  // Método para guardar la categoría
  saveCategory(category: Category) {
    if (this.productForm.valid) {
      this.categoryService.newProducts(category).subscribe(
        (data) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }


}
