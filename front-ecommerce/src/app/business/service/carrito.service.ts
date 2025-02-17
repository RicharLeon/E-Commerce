import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { CarritoItemsInterface } from '../../models/carrito-items';
import { CarritoInterface } from '../../models/carrito';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
 private apiUrl = 'http://localhost:8082/carrito';

  constructor(private http: HttpClient) { }

    
    addNewCarrito(carritoItem: CarritoInterface): Observable<CarritoInterface[]> {
      return this.http.post<CarritoInterface[]>(`${this.apiUrl}`, carritoItem).pipe(
        catchError(error => {
          console.error('Error updating product', error);
          return throwError(() => error);
        })
      );
    }

    getCarritos(): Observable<CarritoInterface[]> {
      return this.http.get<CarritoInterface[]>(`${this.apiUrl}`).pipe(
        catchError(error => {
          console.error('Error getting product', error);
          return throwError(() => error);
        })
      );
    }

    getCarritoById(id: number): Observable<CarritoInterface> {
      return this.http.get<CarritoInterface>(`${this.apiUrl}/${id}`).pipe(
        catchError(error => {
          console.error('Error getting product', error);
          return throwError(() => error);
        })
      );
    }
}
