package dev.richar.market.models.services;

import dev.richar.market.models.entity.Carrito;
import dev.richar.market.models.entity.Pay;

import java.util.List;

public interface IPayService {

    List<Pay> findAll();
    Pay findById(Integer id);
    Pay save(Pay pay);
    void delete(Integer id);
    Pay update (Pay pay, Integer id);
}
