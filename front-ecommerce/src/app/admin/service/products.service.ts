import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Product } from '../../models/product';


@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8082/products';

  constructor(private http: HttpClient) { }

  // Get all products
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}`).pipe(
      catchError(error => {
        console.error('Error fetching products', error);
        return throwError(() => error);
      })
    );
  }

  // Get a product by ID
  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`).pipe(
      catchError(error => {
        console.error('Error fetching product by ID', error);
        return throwError(() => error);
      })
    );
  }

  // Add a new product
  newProducts(product: Product): Observable<Product[]> {
    return this.http.post<Product[]>(this.apiUrl, product).pipe(
      catchError(error => {
        console.error('Error adding new product', error);
        return throwError(() => error);
      })
    );
  }

  // Update a product
  updateProduct(id: number,product: Product): Observable<Product[]> {
    return this.http.put<Product[]>(`${this.apiUrl}/${id}`, product).pipe(
      catchError(error => {
        console.error('Error updating product', error);
        return throwError(() => error);
      })
    );
  }

  // Delete a product
  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}