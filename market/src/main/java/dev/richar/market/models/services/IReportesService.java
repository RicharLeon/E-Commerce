package dev.richar.market.models.services;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface IReportesService {
    byte[] reporteProductos() throws JRException, FileNotFoundException;
    byte[] reporteProductosGenerales() throws JRException;
    byte[] reporteCincoMasVendidos() throws JRException;
    byte[] reporteCincoMasFrecuentes() throws JRException, SQLException;
    byte[] reporteGenerar(String reporteNAme) throws JRException;
}
