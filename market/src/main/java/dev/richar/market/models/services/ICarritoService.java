package dev.richar.market.models.services;

import dev.richar.market.models.dao.CarritoDao;
import dev.richar.market.models.dao.CarritoItemsDao;
import dev.richar.market.models.entity.Carrito;
import dev.richar.market.models.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICarritoService {

    List<Carrito> findAll();
    Carrito findById(Integer id);
    Optional<Carrito> findByIdOptional(Integer id);
    Carrito save(Carrito carrito);
    void delete(Integer id);
    Carrito update (Carrito carrito, Integer id);
}
