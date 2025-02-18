package dev.richar.market.controllers;

import dev.richar.market.models.services.IReportesService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reportes")
public class ReportsController {

    @Autowired
    private final IReportesService reportesService; // Servicio que contiene la lógica del informe

    public ReportsController(IReportesService reportesService) {
        this.reportesService = reportesService;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarReporteProductos() {
        try {
            byte[] reporte = reportesService.reporteProductosGenerales();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=informe_productos.pdf")
                    .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    .header(HttpHeaders.PRAGMA, "no-cache")
                    .body(reporte);

        } catch (JRException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/mas-vendidos", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarReporteCincoMasVendidos() {
        try {
            byte[] reporte = reportesService.reporteCincoMasVendidos();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_cinco_mas_vendidos.pdf")
                    .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    .header(HttpHeaders.PRAGMA, "no-cache")
                    .body(reporte);

        } catch (JRException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/mas-frecuentes", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarReporteCincoMasFrecuentes() {
        try {
            byte[] reporte = reportesService.reporteCincoMasFrecuentes();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_cinco_clientes_frecuentes.pdf")
                    .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    .header(HttpHeaders.PRAGMA, "no-cache")
                    .body(reporte);

        } catch (JRException e) {
            return ResponseEntity.internalServerError().build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    @GetMapping(value = "/mas-frecuentes")
//    public ResponseEntity<byte[]> generarReporteCincoMasFrecuentes() {
//        try {
//            byte[] reporte = reportesService.reporteGenerar("reporte_cinco_clientes_ocacionales");
//
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.add("Content-Disposition", "inline; filename=reporte_cinco_clientes_ocacionales.pdf");
//
//            return new ResponseEntity<>(reporte, headers, HttpStatus.OK);
//        } catch (JRException e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }

}
