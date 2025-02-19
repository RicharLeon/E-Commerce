import { Injectable } from '@angular/core';

import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { PagedResponse, Product } from '../../models/product';
import { Page } from '../../models/page';


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

  // Get products with filters
  getProductsFilter(
    categoryId?: number,
    minPrice?: number,
    maxPrice?: number,
    minStock?: number,
    status?: string,
    categoryName?: string,
    page: number = 0,
    size: number = 10
  ): Observable<Product> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (categoryId !== undefined) params = params.set('categoryId', categoryId.toString());
    if (minPrice !== undefined) params = params.set('minPrice', minPrice.toString());
    if (maxPrice !== undefined) params = params.set('maxPrice', maxPrice.toString());
    if (minStock !== undefined) params = params.set('minStock', minStock.toString());
    if (status !== undefined) params = params.set('status', status);
    if (categoryName !== undefined) params = params.set('categoryName', categoryName);

    return this.http.get<Product>(this.apiUrl, { params });
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