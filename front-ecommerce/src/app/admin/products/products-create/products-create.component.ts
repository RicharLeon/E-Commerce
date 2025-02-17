import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ProductService } from '../../service/products.service';
import { CategoryService } from '../../service/category.service';
import { Category } from '../../../models/category';
import { Product } from '../../../models/product';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-products-create',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './products-create.component.html',
  styleUrls: ['./products-create.component.css'] // Corregido: styleUrls en plural
})
export class ProductsCreateComponent implements OnInit {



  categories: Category[] = []; // Renombrado para mayor claridad

  productForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router
  ) {
    this.productForm = this.fb.group({
      nombre: ['', Validators.required],
      idCategory: ['', Validators.required],
      price: ['', Validators.required],
      stock: ['', Validators.required],
      status: ['', Validators.required],
      description: ['', Validators.required], // Nuevo control para la descripción
      photo: ['', Validators.required],
      disponible: [false], // Nuevo control para el checkbox
    });
  }

  ngOnInit(): void {
    this.getCategories();
  }

  onSubmit() {
    if (this.productForm.valid) {
      console.log(this.productForm.value);


      // convertirlo a number

      // Obtener el ID y el nombre de la categoría seleccionada y convertirlo a numebr
      const selectedCategoryId = Number(this.productForm.value.idCategory);
         
      // convertir el precio a número
      const priceString = this.productForm.value.price.replace(/\D/g, '');
      const priceNumber = parseFloat(priceString); // Divide si manejas centavos
      
      console.log('id convertido', selectedCategoryId);

      const product: Product = {
        //id: 0, // El ID se asigna en el servidor
        name: this.productForm.value.nombre,
        idCategory: selectedCategoryId,
        //nameCategory: selectedCategoryName ? selectedCategoryName.name : '',
        price: priceNumber,
        stock: this.productForm.value.stock,
        status: this.productForm.value.status === 'activo' ? true : false,
        description: this.productForm.value.description,
        photo: this.productForm.value.photo        
      };

      confirm('¿Desea guardar el producto?');
      console.log('Producto a guardar', product); 

      this.saveProduct(product);
    } else {
      console.log('Formulario no válido');
    }
  }

  // Getter para acceder fácilmente a los controles del formulario
  get f() {
    return this.productForm.controls;
  }

  // Método para cargar la imagen y actualizar el control "photo"
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const result = reader.result as string;
        // Extrae solo la parte base64 eliminando el prefijo (por ejemplo: "data:image/png;base64,")
        const base64Data = result.includes(',') ? result.split(',')[1] : result;
        this.productForm.patchValue({
          photo: base64Data,
        });
      };
    }
  }
  

  // Método para guardar el producto usando el servicio
  saveProduct(product: Product): void {
    if (this.productForm.valid) {
      this.productService.newProducts(product).subscribe((res) => {
        console.log('Producto creado', res);
        this.goBack();
      });
    } else {
      console.log('Formulario no válido');
    }
  }

  // Método para cargar las categorías
  getCategories(): void {
    this.categoryService.getProducts().subscribe((res) => {
      console.log('Categorías', res);
      this.categories = res;
    });
  }

  // Dar formato al precio
  formatPrice($event: Event) {
    const input = $event.target as HTMLInputElement;
    const value = input.value.replace(/\D/g, '');
    input.value = `$${value.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')}`;
  }


  // Método para volver a la lista de productos
  goBack(): void {
    this.router.navigate(['/productos-list']);
  }
}
