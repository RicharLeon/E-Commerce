package dev.richar.market.models.dao;

import dev.richar.market.models.dto.ProductosInformeDTO;
import dev.richar.market.models.dto.ProductsConsultaDTO;
import dev.richar.market.models.dto.UsuariosInformeDTO;
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


    @Query(value = "SELECT name, price, status, stock FROM products WHERE status = true ",
            nativeQuery = true)
    List<ProductosInformeDTO> findActiveProducts();

    @Query(value = "SELECT pro.name, pro.price, pro.status, pro.stock " +
            "FROM pagos p " +
            "INNER JOIN carrito c ON (p.id_carrito = c.id_carrito) " +
            "INNER JOIN carrito_items ci ON (ci.id_carrito = c.id_carrito) " +
            "INNER JOIN products pro ON (pro.id_product = ci.id_producto) " +
            "WHERE p.estado_pago = 'COMPLETADO' " +
            "ORDER BY ci.cantidad DESC " +
            "LIMIT 5 ",
            nativeQuery = true)
    List<ProductosInformeDTO> findProductsMasVendidos();

    @Query(value = "SELECT c.id_usuario, ou.name, SUM(ci.cantidad) AS total " +
            "FROM pagos p " +
            "INNER JOIN carrito c ON (p.id_carrito = c.id_carrito) " +
            "INNER JOIN carrito_items ci ON (ci.id_carrito = c.id_carrito) " +
            "INNER JOIN products pro ON (pro.id_product = ci.id_producto) " +
            "INNER JOIN ourusers ou ON (ou.id=c.id_usuario) " +
            "WHERE p.estado_pago = 'COMPLETADO' " +
            "GROUP BY c.id_usuario " +
            "ORDER BY TOTAL DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<UsuariosInformeDTO> findUsuariosMasFrecuentes();


}
