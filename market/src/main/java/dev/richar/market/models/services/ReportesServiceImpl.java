package dev.richar.market.models.services;

import dev.richar.market.models.dao.ProductsDao;
import dev.richar.market.models.dto.ProductosInformeDTO;
import dev.richar.market.models.dto.UsuariosInformeDTO;
import dev.richar.market.models.entity.Products;
import dev.richar.market.utils.GenerecReportGenerator;
import dev.richar.market.utils.PetReportGenerator;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ReportesServiceImpl implements IReportesService {


    private final ProductsDao productsDao;
    private final PetReportGenerator petReportGenerator;
    private final GenerecReportGenerator generecReportGenerator;

    public ReportesServiceImpl(ProductsDao productsDao, PetReportGenerator petReportGenerator, GenerecReportGenerator generecReportGenerator) {
        this.productsDao = productsDao;
        this.petReportGenerator = petReportGenerator;
        this.generecReportGenerator = generecReportGenerator;
    }


    @Override
    public byte[] reporteProductos() throws JRException, FileNotFoundException {
        return petReportGenerator.exportToPdf(productsDao.findActiveProducts());
    }

    @Override
    public byte[] reporteProductosGenerales() throws JRException {
        List<ProductosInformeDTO> productos = productsDao.findActiveProducts();
        Map<String, Object> params = new HashMap<>();
        params.put("titulo", "Informe de Productos 2024");

        return generecReportGenerator.exportToPdf(
                productos,
                "/reports/reporte_ecommerce.jrxml",
                params
        );
    }

    @Override
    public byte[] reporteCincoMasVendidos() throws JRException {
        List<ProductosInformeDTO> productos = productsDao.findProductsMasVendidos();
        Map<String, Object> params = new HashMap<>();
        params.put("titulo", "Informe 5 Productos mas Vendidos 2024");

        return generecReportGenerator.exportToPdf(
                productos,
                "/reports/reporte_ecommerce.jrxml",
                params
        );
    }

    @Override
    public byte[] reporteCincoMasFrecuentes() throws JRException {
        List<UsuariosInformeDTO> productos = productsDao.findUsuariosMasFrecuentes();
        Map<String, Object> params = new HashMap<>();
        params.put("titulo", "Informe 5 Productos mas Vendidos 2024");

        return generecReportGenerator.exportToPdf(
                productos,
                "/reports/reporte_ecommerce.jrxml",
                params
        );
    }


}
