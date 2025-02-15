package dev.richar.market.models.services;

import dev.richar.market.models.dto.ProductsConsultaDTO;
import dev.richar.market.models.entity.Category;
import dev.richar.market.models.entity.Products;

import java.util.List;

public interface ICategoryService {

    List<Category> findAll();
    Category findById(Integer id);
    Category save(Category category);
    void delete(Integer id);
    Category update (Category category, Integer id);
}
