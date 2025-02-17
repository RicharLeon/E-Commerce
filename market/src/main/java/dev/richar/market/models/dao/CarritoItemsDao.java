package dev.richar.market.models.dao;

import dev.richar.market.models.dto.CarritoItemsConsultaDTO;
import dev.richar.market.models.entity.Carrito;
import dev.richar.market.models.entity.CarritoItems;
import dev.richar.market.models.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarritoItemsDao extends JpaRepository<CarritoItems, Integer> {

    @Query(value = "SELECT * FROM `auth-management`.carrito c " +
            "INNER JOIN `auth-management`.pagos p ON (c.id_carrito = p.id_carrito) " +
            "WHERE p.estado_pago = :estadoPago AND c.id_carrito = :idCarrito", nativeQuery = true)
    Optional<Carrito> findCompletedPaysByCarritoId(String estadoPago, Integer idCarrito);

    @Query(value = "SELECT id_product, id_carrito_item, cantidad, id_category, name, photo, price, status, stock, description " +
            "FROM carrito_items c " +
            "INNER JOIN products p ON (p.id_product = c.id_producto) " +
            "WHERE id_carrito = :idCarrito", nativeQuery = true)
    List<CarritoItemsConsultaDTO> findCarritoItemsWithProductDetailsByCarritoId(Integer idCarrito);


}
