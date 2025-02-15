package dev.richar.market.models.services;

import dev.richar.market.models.dto.ProductsConsultaDTO;
import dev.richar.market.models.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductsService {

    List<ProductsConsultaDTO> findAll();
    ProductsConsultaDTO findById(Integer id);
    Products save(Products empleado);
    void delete(Integer id);
    Products update (Products empleado, Integer id);
    Page<ProductsConsultaDTO> getFilteredProducts(Integer categoryId,
                                                Double minPrice,
                                                Double maxPrice,
                                                Integer minStock,
                                                String status,
                                                String categoryName,
                                                Pageable pageable);
}
