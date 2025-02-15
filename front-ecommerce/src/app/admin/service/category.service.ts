import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../../models/category';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = 'http://localhost:8082/category';

  constructor(private http: HttpClient) { }

  // Get all products
  getProducts(): Observable<Category[]> {
    return this.http.get<Category[]>(this.apiUrl).pipe(
      catchError(error => {
        console.error('Error fetching products', error);
        return throwError(() => error);
      })
    );
  }
  // Get a product by ID
  getProductById(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.apiUrl}/${id}`).pipe(
      catchError(error => {
        console.error('Error fetching product by ID', error);
        return throwError(() => error);
      })
    );
  }

  // Add a new product
  newProducts(category: Category): Observable<Category[]> {
    return this.http.post<Category[]>(this.apiUrl, category).pipe(
      catchError(error => {
        console.error('Error adding new product', error);
        return throwError(() => error);
      })
    );
  }

  // Update a product
  updateProduct(category: Category): Observable<Category[]> {
    return this.http.put<Category[]>(`${this.apiUrl}/${category.id}`, category).pipe(
      catchError(error => {
        console.error('Error updating product', error);
        return throwError(() => error);
      })
    );
  }

  // Delete a product
  deleteCategory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}