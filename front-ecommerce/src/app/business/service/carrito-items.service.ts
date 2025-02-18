import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { CarritoItemsInterface } from '../../models/carrito-items';

@Injectable({
  providedIn: 'root'
})
export class CarritoItemsService {

 private apiUrl = 'http://localhost:8082/carrito-items';

  constructor(private http: HttpClient) { }

    
    addItem(carritoItem: CarritoItemsInterface): Observable<CarritoItemsInterface[]> {
      return this.http.post<CarritoItemsInterface[]>(`${this.apiUrl}`, carritoItem).pipe(
        catchError(error => {
          console.error('Error updating product', error);
          return throwError(() => error);
        })
      );
    }

    getItems(id: number): Observable<CarritoItemsInterface[]> {
      return this.http.get<CarritoItemsInterface[]>(`${this.apiUrl}/items-by-user/${id}`).pipe(
        catchError(error => {
          console.error('Error getting product', error);
          return throwError(() => error);
        })
      );
    }

    editItems(id: number, carritoItem: CarritoItemsInterface): Observable<CarritoItemsInterface[]> {
      return this.http.put<CarritoItemsInterface[]>(`${this.apiUrl}/${id}`, carritoItem).pipe(
        catchError(error => {
          console.error('Error updating product', error);
          return throwError(() => error);
        })
      );
    }

    deleteItems(id: number): Observable<CarritoItemsInterface[]> {
      return this.http.delete<CarritoItemsInterface[]>(`${this.apiUrl}/${id}`).pipe(
        catchError(error => {
          console.error('Error deleting product', error);
          return throwError(() => error);
        })
      );
    }
}
