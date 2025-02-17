package dev.richar.market.models.dao;

import dev.richar.market.models.entity.Carrito;
import dev.richar.market.models.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarritoDao extends JpaRepository<Carrito, Integer> {

    Optional<Carrito> findByIdUsuario( Integer idUsuario);




}
