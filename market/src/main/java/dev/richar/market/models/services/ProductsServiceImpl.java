package dev.richar.market.models.services;

import dev.richar.market.models.dao.ProductsDao;
import dev.richar.market.models.dto.ProductsConsultaDTO;
import dev.richar.market.models.entity.Products;
import dev.richar.market.models.specification.ProductSpecifications;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static dev.richar.market.models.specification.ProductSpecifications.fetchCategory;

@RequiredArgsConstructor

@Service
public class ProductsServiceImpl implements IProductsService {

    @Autowired
    private final ProductsDao productsDao;

    @Override
    public List<ProductsConsultaDTO> findAll() {
        return productsDao.findAllProducts();
    }

    @Override
    public ProductsConsultaDTO findById(Integer id) {
        return productsDao.findProductsForId(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat
                        .format("Product with id {0} not found", id)));
    }

    @Override
    public Products save(Products product) {
        return productsDao.save(product);
    }

    @Override
    public void delete(Integer id) {
        productsDao.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat
                        .format("Product with id {0} not found", id)));
        productsDao.deleteById(id);
    }

    @Override
    public Products update(Products product, Integer id) {
        Products products = productsDao.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat
                        .format("Product with id {0} not found", id)));


        products.setName(product.getName());
        products.setIdCategory(product.getIdCategory());
        products.setPrice(product.getPrice());
        products.setStock(product.getStock());
        products.setStatus(product.getStatus());
        products.setDescription(product.getDescription());
        products.setPhoto(product.getPhoto());
        products.setUpdatedAt(product.getUpdatedAt());
        return productsDao.save(products);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductsConsultaDTO> getFilteredProducts(
            Integer categoryId,
            Double minPrice,
            Double maxPrice,
            Integer minStock,
            String status,
            String categoryName,
            Pageable pageable) {

        // Combinar filtros con el fetch de Category
        Specification<Products> spec = Specification
                .where(ProductSpecifications.withFilters(categoryId, minPrice, maxPrice, minStock, status, categoryName))
                .and(ProductSpecifications.fetchCategory());

        // Obtener resultados paginados
        Page<Products> productsPage = productsDao.findAll(spec, pageable);

        // Convertir Page<Products> a Page<ProductsConsultaDTO>
        return productsPage.map(this::convertToDto);
    }

    private ProductsConsultaDTO convertToDto(Products product) {
        // Implementa la conversión de Products a ProductsConsultaDTO
        return new ProductsConsultaDTO(
                product.getId(),
                product.getName(),
                product.getIdCategory(),
                product.getCategory().getName(), // Asegúrate de cargar la categoría
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getDescription(),
                product.getPhoto(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}













