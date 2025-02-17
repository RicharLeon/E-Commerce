package dev.richar.market.models.services;

import dev.richar.market.models.dto.CarritoItemsConsultaDTO;
import dev.richar.market.models.entity.CarritoItems;
import dev.richar.market.models.entity.Category;

import java.util.List;

public interface ICarritoItemsService {

    List<CarritoItems> findAll();
    CarritoItems findById(Integer id);
    CarritoItems save(
            CarritoItems carritoItems);

    void delete(Integer id);
    CarritoItems update(CarritoItems carritoItems, Integer idCarritoItem);
    CarritoItems addItemToUserCart(Integer idUser, CarritoItems carritoItems);

    List<CarritoItemsConsultaDTO> getItemsByUserAndCarrito(Integer idUser);
}
