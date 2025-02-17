import { Component, OnInit } from '@angular/core';
import { CarritoItemsInterface } from '../../../models/carrito-items';
import { CarritoItemsService } from '../../service/carrito-items.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-shopping-cards',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './shopping-cards.component.html',
  styleUrl: './shopping-cards.component.css'
})
export class ShoppingCardsComponent implements OnInit {
  carritoId: any;
  dataItems: any[] = [];
  idProductoRecuperdao: any;
  cantidadItems: { [id: number]: number } = {};

  constructor(
    private carritoItemsService: CarritoItemsService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer,
  ) { }

  ngOnInit(): void {
    this.carritoId = Number(this.route.snapshot.paramMap.get('id'));
    console.log('carritoId:', this.carritoId); // Verifica que sea el valor esperado
    this.getItems(this.carritoId);

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

    ;



}
