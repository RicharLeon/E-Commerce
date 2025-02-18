import { Component, OnInit } from '@angular/core';
import { ReportesService } from '../../business/service/reportes.service';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [],
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.css'
})
export class ReportesComponent implements OnInit {

  

  constructor(private reporteService: ReportesService) { }

  ngOnInit(): void {
  }


  downloadProductos() {
    this.reporteService.descargarReporteProductos();
  }
  downloadProductosMasVendidos() {
    this.reporteService.descargarReporteCincoMasVendidos();
  }
  downloadClientesFrecuentes() {
    this.reporteService.descargarReporteClientesFrecuentes();
  }
  
  //merodo para ir atras
  goBack() {
    window.history.back();
  }

}
