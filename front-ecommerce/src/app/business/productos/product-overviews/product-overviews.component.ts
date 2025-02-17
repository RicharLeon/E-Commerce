import { Component, OnInit } from '@angular/core';
import { CategoryListComponent } from '../../../admin/categorys/category-list/category-list.component';
import { ProductsCreateComponent } from "../../../admin/products/products-create/products-create.component";
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductService } from '../../../admin/service/products.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../models/product';
import { CarritoService } from '../../service/carrito.service';
import { CarritoInterface } from '../../../models/carrito';
import { UsersService } from '../../../auth/services/users.service';
import { CarritoItemsService } from '../../service/carrito-items.service';
import { CarritoItemsInterface } from '../../../models/carrito-items';

@Component({
  selector: 'app-product-overviews',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './product-overviews.component.html',
  styleUrl: './product-overviews.component.css'
})
export class ProductOverviewsComponent implements OnInit {

  carritoExistente: any;
  idUser: any;
  profileInfo: any;
  userId: number | null = null;
  loading = false;
  productId!: number;
  producto: any;
  newItem: CarritoItemsInterface = {
    idProducto: 0,
    cantidad: 0,
    idCarrito: 0
  };
  newCarrito: CarritoInterface = {
    idUsuario: 0
  }; // Initialize with appropriate default values
  errorMessage: string | undefined;

  constructor(private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private carritoService: CarritoService,
    private usersService: UsersService,
    private carritoItemsService: CarritoItemsService
  ) { }


  async ngOnInit() {

    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadProducts();

    try {
      const token = localStorage.getItem('token')
      if (!token) {
        throw new Error("No Token Found")
      }

      this.profileInfo = await this.usersService.getYourProfile(token);
      this.idUser = this.profileInfo.ourUsers.id;
    } catch (error: any) {
      this.showError(error.message)
    }

  }

  loadProducts(): void {
    this.loading = true;
    this.productService.getProductById(this.productId).subscribe({
      next: (data) => {
        this.producto = data;
        this.loading = false;
        console.log(data);
      },
      error: (error) => {
        console.log(error);
        this.loading = false;
      }
    });
  }

  addToCart(id: number) {

    this.validarExistenciaCarrito();
    
    console.log(id);


  }

  validarExistenciaCarrito() {
    this.carritoService.getCarritoById(this.idUser).subscribe({
      next: (data) => {

        console.log("esta es la data", data);
        this.carritoExistente = data;
        if (!data || Object.keys(data).length === 0) {
          
          this.createNewCarrito();
          return;
        }else{

          this.newItem = {
            idProducto: this.productId,
            cantidad: 1,
            idCarrito: data.idCarrito
          }
          this.crearNuevoItemCarrito(this.newItem);


          this.router.navigate(['/shopping-card/' + data.idCarrito]);
        }
      },
      error: (error) => {
        console.error('Error al validar la existencia del carrito:', error);
      }
    });
  }
  

  createNewCarrito() {

    this.newCarrito.idUsuario = this.idUser;

    this.carritoService.addNewCarrito(this.newCarrito).subscribe({
      next: (data) => {
        console.log(data);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  crearNuevoItemCarrito(item: CarritoItemsInterface) {
    this.carritoItemsService.addItem(item).subscribe({
      next: (data) => {
        console.log(data);
        alert('Producto agregado al carrito');
      }
    });
  }

  showError(mess: string) {
    this.errorMessage = mess;
    setTimeout(() => {
      this.errorMessage = ''
    }, 3000)
  }

}
