package dev.richar.market.models.specification;

import dev.richar.market.models.dto.ProductsConsultaDTO;
import dev.richar.market.models.entity.Category;
import dev.richar.market.models.entity.Products;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications {

    public static Specification<Products> withFilters(
            Integer categoryId,
            Double minPrice,
            Double maxPrice,
            Integer minStock,
            String status,
            String categoryName) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por categoría (ID)
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("idCategory"), categoryId));
            }

            // Filtro por rango de precio
            if (minPrice != null) {
                predicates.add(criteriaBuilder.ge(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.le(root.get("price"), maxPrice));
            }

            // Filtro por stock mínimo
            if (minStock != null) {
                predicates.add(criteriaBuilder.ge(root.get("stock"), minStock));
            }

            // Filtro por estado
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // Filtro por nombre de categoría (si hay join)
            if (categoryName != null) {
                Join<Products, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.like(categoryJoin.get("name"), "%" + categoryName + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Products> fetchCategory() {
        return (root, query, criteriaBuilder) -> {
            if (!query.getResultType().equals(Long.class)) { // Evita problemas en COUNT para paginación
                root.fetch("category", JoinType.INNER);
            }
            return null;
        };
    }
}