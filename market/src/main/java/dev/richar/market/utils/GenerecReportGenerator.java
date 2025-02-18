package dev.richar.market.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class GenerecReportGenerator{

    public <T> byte[] exportToPdf(List<T> data, String reportPath, Map<String, Object> parameters) throws JRException {
        return JasperExportManager.exportReportToPdf(getReport(data, reportPath, parameters));
    }

    public <T> byte[] exportToXls(List<T> data, String reportPath, Map<String, Object> parameters) throws JRException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);

        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport(data, reportPath, parameters)));
        exporter.setExporterOutput(output);

        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }

    private <T> JasperPrint getReport(List<T> data, String reportPath, Map<String, Object> parameters)
            throws JRException {

        // Carga el reporte desde classpath usando InputStream
        InputStream reportStream = getClass().getResourceAsStream(reportPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Configura el datasource
        JRDataSource dataSource = new JRBeanCollectionDataSource(data);

        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }
}