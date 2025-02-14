import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private BASE_URL = "http://localhost:3030";
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.isAuthenticated());

  constructor(private http: HttpClient) { }

  async login(email: string, password: string): Promise<any> {
    const url = `${this.BASE_URL}/auth/login`;
    try {
      const response = await this.http.post<any>(url, { email, password }).toPromise();
      if (response.token) {
        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);
        this.isAuthenticatedSubject.next(true);
      }
      return response;
    } catch (error) {
      throw error;
    }
  }

  async register(userData: any, token: string): Promise<any> {
    const url = `${this.BASE_URL}/auth/register`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    try {
      const response = await this.http.post<any>(url, userData, { headers }).toPromise();
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getAllUsers(token: string): Promise<any> {
    const url = `${this.BASE_URL}/admin/get-all-users`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    try {
      const response = await this.http.get<any>(url, { headers }).toPromise();
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getYourProfile(token: string): Promise<any> {
    const url = `${this.BASE_URL}/adminuser/get-profile`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    try {
      const response = await this.http.get<any>(url, { headers }).toPromise();
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getUsersById(userId: string, token: string): Promise<any> {
    const url = `${this.BASE_URL}/admin/get-users/${userId}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    try {
      const response = await this.http.get<any>(url, { headers }).toPromise();
      return response;
    } catch (error) {
      throw error;
    }
  }

  async deleteUser(userId: string, token: string): Promise<any> {
    const url = `${this.BASE_URL}/admin/delete/${userId}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    try {
      const response = await this.http.delete<any>(url, { headers }).toPromise();
      return response;
    } catch (error) {
      throw error;
    }
  }

  async updateUSer(userId: string, userData: any, token: string): Promise<any> {
    const url = `${this.BASE_URL}/admin/update/${userId}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    try {
      const response = await this.http.put<any>(url, userData, { headers }).toPromise();
      return response;
    } catch (error) {
      throw error;
    }
  }

  /***AUTENTICACIÓN */
  logOut(): void {
    console.log("Cerrando sesión..."); // Para verificar si el método se ejecuta
  
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
  
      this.isAuthenticatedSubject.next(false);
  
      // Recarga la página para asegurarse de que todos los datos en memoria se borren
      window.location.reload();
    }
  }
  

  isAuthenticated(): boolean {
    if (typeof localStorage !== 'undefined') {
      const token = localStorage.getItem('token');
      return !!token;
    }
    return false;
  }

  isAdmin(): boolean {
    if (typeof localStorage !== 'undefined') {
      const role = localStorage.getItem('role');
      return role === 'ADMIN';
    }
    return false;
  }

  isUser(): boolean {
    if (typeof localStorage !== 'undefined') {
      const role = localStorage.getItem('role');
      return role === 'USER';
    }
    return false;
  }

  get isAuthenticated$() {
    return this.isAuthenticatedSubject.asObservable();
  }
}
