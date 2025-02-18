import { Component, OnInit } from '@angular/core';
import { CarritoItemsInterface } from '../../../models/carrito-items';
import { CarritoItemsService } from '../../service/carrito-items.service';
import { ActivatedRoute } from '@angular/router';

import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CarritoService } from '../../service/carrito.service';
import { CarritoInterface } from '../../../models/carrito';
import { UsersService } from '../../../auth/services/users.service';

@Component({
  selector: 'app-shopping-cards',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './shopping-cards.component.html',
  styleUrl: './shopping-cards.component.css'
})
export class ShoppingCardsComponent implements OnInit {

  descuento: any;
  totalConDescuentos: number = 0;

  idUser: any;
  profileInfo: any;
  total: number = 0;
  carritoId: any;
  dataItems: any[] = [];
  validarCarrito:any;
  idProductoRecuperdao: any;
  cantidadItems: { [id: number]: number } = {};

  constructor(
    private carritoItemsService: CarritoItemsService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private carritoService: CarritoService,
    private usersService: UsersService
  ) {
    
   }

  async ngOnInit() {
    this.carritoId = Number(this.route.snapshot.paramMap.get('id'));
    console.log('carritoId:', this.carritoId); // Verifica que sea el valor esperado
    this.getItems(this.carritoId);

    this.validarDescuentos() ; 

    try {
      const token = localStorage.getItem('token')
      if (!token) {
        throw new Error("No Token Found")
      }

      this.profileInfo = await this.usersService.getYourProfile(token);
      this.idUser = this.profileInfo.ourUsers.id;
    } catch (error: any) {
      console.log(error.message)
    }

  }
  disminuirCantidad(product: any): void {
    if (product.cantidad > 1) {
      product.cantidad--;
      this.editItems(product.idCarritoItem, product.cantidad);
    }
  }
  
  aumentarCantidad(product: any): void {
    product.cantidad++;
    this.editItems(product.idCarritoItem , product.cantidad);
  }


  // Cargar productos para editar

  getItems(id: number): void {
    this.carritoItemsService.getItems(id).subscribe((res: any) => {
      this.dataItems = res;
      
      this.sumaTotal(this.dataItems); 

      this.calcularPorcentaje(this.total, this.descuento);
      this.dataItems.forEach((item: any) => {
        this.cantidadItems[item.idProduct] = item.cantidad;
      });
      console.log(this.dataItems);
    });
  }


  editItems(id: number, cantidad: number): void {
    const carritoItem: CarritoItemsInterface = { cantidad };
    this.carritoItemsService.editItems(id, carritoItem).subscribe((res: any) => {  
    });
  }
  


    getSafeImageUrl(photo: string): SafeUrl {
      // Asegúrate de ajustar el tipo MIME según corresponda (image/png, image/jpeg, etc.)
      const imageUrl = 'data:image/png;base64,' + photo;
      return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
    }

    sumaTotal(data: any ): number {

      this.total = 0;

      data.forEach((item: any) => {
        this.total += item.cantidad * item.price;
      });
      console.log("esta es la suma", this.total);

      
      return this.total- this.descuento;

    }

    calcularPorcentaje(cantidad: number, porcentaje: number): number {
      let resultado = (cantidad * porcentaje) / 100;
      this.totalConDescuentos = cantidad - resultado;

      console.log('total con descuentos:', this.totalConDescuentos);
      return this.totalConDescuentos;
    }

    validarDescuentos(): void {
      this.validarCarrito = {
        idCarrito: this.carritoId,
        idUsuario: this.idUser
      } as CarritoInterface;
      this.carritoService.validarSiCuentaConDescuentos(this.validarCarrito).subscribe((res: any) => {

        this.descuento =  res;
        console.log('descuentos:', res);
      });
    }



}
