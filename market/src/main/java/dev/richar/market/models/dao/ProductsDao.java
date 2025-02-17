package dev.richar.market.models.dao;

import dev.richar.market.models.dto.ProductsConsultaDTO;
import dev.richar.market.models.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProductsDao extends JpaRepository<Products, Integer>, JpaSpecificationExecutor<Products> {

    @Query("select new dev.richar.market.models.dto.ProductsConsultaDTO(p.id, p.name, p.idCategory, c.name, p.price, p.stock, p.status, p.description, p.photo, p.createdAt, p.updatedAt) from Products p " +
            "inner join p.category c "
    )
    List<ProductsConsultaDTO> findAllProducts();

    @Query("select new dev.richar.market.models.dto.ProductsConsultaDTO(p.id, p.name, p.idCategory, c.name, p.price, p.stock, p.status, p.description, p.photo, p.createdAt, p.updatedAt) from Products p " +
            "inner join p.category c " +
            "where p.id = ?1"
    )
    Optional<ProductsConsultaDTO> findProductsForId(Integer id);


}
