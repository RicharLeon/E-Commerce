import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductService } from '../../service/products.service';
import { CategoryService } from '../../service/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../../../models/category';
import { Product } from '../../../models/product';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './products-edit.component.html',
  styleUrl: './products-edit.component.css'
})
export class ProductsEditComponent implements OnInit {
  productForm: FormGroup;
  productId!: number;
  categories: Category[] = [];

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Definición del formulario con sus controles
    this.productForm = this.fb.group({
      nombre: ['',],
      idCategory: ['',],
      price: ['',],
      stock: ['',],
      status: ['',],
      description: ['',],
      photo: ['',],
    });
  }

  ngOnInit(): void {
    // Cargar las categorías
    this.getCategories();
    // Obtener el id del producto desde la URL
    this.productId = Number(this.route.snapshot.paramMap.get('id'));

    this.getProductForId();
  }

  // Cargar categorías para el select
  getCategories(): void {
    this.categoryService.getProducts().subscribe((res: Category[]) => {
      this.categories = res;
    });
  }
  // Cargar producto para editar
  getProductForId(): void {
    this.productService.getProductById(this.productId).subscribe((res: Product) => {
      this.productForm.patchValue({
        nombre: res.name,
        idCategory: res.idCategory,
        price: res.price,
        stock: res.stock,
        status: res.status ? 'activo' : 'inactivo',
        description: res.description,
        photo: res.photo
      });
    });
  }

  // Editar producto
  editProduct(id: number, product: Product): void {
    if (this.productForm.valid) {
      this.productService.updateProduct(id, product).subscribe((res) => {
        console.log('Producto editado', res);
        this.goBack();
      });
    } else {
      console.log('Formulario no válido');
    }
  }

  // Manejar el cambio del archivo para actualizar la imagen
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        const result = reader.result as string;
        // Extrae la parte Base64 eliminando el prefijo (por ejemplo, "data:image/png;base64,")
        const base64Data = result.includes(',') ? result.split(',')[1] : result;
        this.productForm.patchValue({ photo: base64Data });
      };
      reader.readAsDataURL(file);
    }
  }

  // Enviar el formulario y actualizar el producto
  onSubmit(): void {

    // convertir el precio a número
  
    if (this.productForm.valid) {

      const priceString = String(this.productForm.value.price || '0').replace(/\D/g, '');
      const priceNumber = parseFloat(priceString); // Divide si manejas centavos
  
      const updatedProduct: Product = {
        id: this.productId,
        name: this.productForm.value.nombre,
        idCategory: Number(this.productForm.value.idCategory),
        price: priceNumber,
        stock: this.productForm.value.stock,
        status: this.productForm.value.status === 'activo' ? true : false,
        description: this.productForm.value.description,
        photo: this.productForm.value.photo
      };

      console.log('Producto actualizado', updatedProduct);

      this.productService.updateProduct(this.productId, updatedProduct).subscribe((res) => {
        console.log('Producto actualizado', res);
        this.router.navigate(['/productos-list']);
      });
    } else {
      console.log('Formulario no válido');
    }
  }

  // Getter para acceder fácilmente a los controles del formulario
  get f() {
    return this.productForm.controls;
  }

  // Dar formato al precio
  formatPrice($event: Event) {
    const input = $event.target as HTMLInputElement;
    const value = input.value.replace(/\D/g, '');
    input.value = `$${value.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')}`;
  }

  // Volver a la lista
  goBack(): void {
    this.router.navigate(['/productos-list']);
  }
}