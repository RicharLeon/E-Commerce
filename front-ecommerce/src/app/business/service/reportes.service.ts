import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportesService {
  private apiUrl = 'http://localhost:8082/reportes';
  
    constructor(private http: HttpClient) { }

      
    descargarReporteProductos(): void {
      this.http.get(this.apiUrl, { responseType: 'blob' })
        .subscribe({
          next: (blob: Blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'informe_productos.pdf';
            a.click();
            window.URL.revokeObjectURL(url);
          },
          error: (err) => {
            console.error('Error al descargar el informe:', err);
          }
        });

    }

    descargarReporteCincoMasVendidos(): void {
      this.http.get(`${this.apiUrl}/mas-vendidos`, { responseType: 'blob' })
        .subscribe({
          next: (blob: Blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'reporte_cinco_mas_vendidos.pdf';
            a.click();
            window.URL.revokeObjectURL(url);
          },
          error: (err) => {
            console.error('Error al descargar el informe:', err);
          }
        });
    }

    descargarReporteClientesFrecuentes(): void {
      this.http.get(`${this.apiUrl}/mas-frecuentes`, { responseType: 'blob' })
        .subscribe({
          next: (blob: Blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'informe_productos.pdf';
            a.click();
            window.URL.revokeObjectURL(url);
          },
          error: (err) => {
            console.error('Error al descargar el informe:', err);
          }
        });
    }

}
