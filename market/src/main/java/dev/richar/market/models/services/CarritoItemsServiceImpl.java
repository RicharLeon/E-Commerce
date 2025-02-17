package dev.richar.market.models.services;

import dev.richar.market.models.dao.CarritoDao;
import dev.richar.market.models.dao.CarritoItemsDao;
import dev.richar.market.models.dto.CarritoItemsConsultaDTO;
import dev.richar.market.models.entity.Carrito;
import dev.richar.market.models.entity.CarritoItems;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarritoItemsServiceImpl implements ICarritoItemsService {


    @Autowired
    private final CarritoItemsDao carritoItemsDao;
    @Autowired
    private final ICarritoService carritoService;

    @Override
    public List<CarritoItems> findAll() {
        return carritoItemsDao.findAll();
    }

    @Override
    public CarritoItems findById(Integer id) {
        return carritoItemsDao.findById(id).orElseThrow(
                () -> new RuntimeException(MessageFormat
                        .format("CarritoItems with id {0} not found", id))
        );
    }

    @Override
    public CarritoItems save(CarritoItems carritoItems) {
        return
        carritoItemsDao.save(carritoItems);
    }

    @Override
    public void delete(Integer id) {
        carritoItemsDao.findById(id).orElseThrow(
                () -> new RuntimeException(MessageFormat
                        .format("CarritoItems with id {0} not found", id))
        );
        carritoItemsDao.deleteById(id);
    }

    @Override
    public CarritoItems update(CarritoItems carritoItems, Integer idCarritoItem) {

        CarritoItems carritoItems1 = carritoItemsDao.findById(idCarritoItem).orElseThrow(
                () -> new RuntimeException(MessageFormat
                        .format("CarritoItems with id {0} not found", idCarritoItem))
        );

        carritoItems1.setCantidad(carritoItems.getCantidad());

        return carritoItemsDao.save(carritoItems1);
    }



    @Override
    public CarritoItems addItemToUserCart(Integer idUser, CarritoItems carritoItems) {
        // Se busca el carrito del usuario
        Optional<Carrito> optionalCart = carritoService.findByIdOptional(idUser);
        CarritoItems nuevoItem = new CarritoItems();
        Carrito carrito;

        if (optionalCart.isPresent()) {
            carrito = optionalCart.get();
            // Se verifica si el carrito tiene un pago completado
            if (carritoItemsDao.findCompletedPaysByCarritoId("COMPLETADO", carrito.getIdCarrito()).isPresent()) {
                // Si el carrito tiene un pago completado, se crea un nuevo carrito
                carrito = new Carrito();
                carrito.setIdUsuario(idUser);
                carrito = carritoService.save(carrito);
            }
            // Si no tiene pago completado, se usa el carrito existente
        } else {
            // Si no existe carrito, se crea uno nuevo
            carrito = new Carrito();
            carrito.setIdUsuario(idUser);
            carrito = carritoService.save(carrito);
        }



        nuevoItem.setCantidad(carritoItems.getCantidad());
        nuevoItem.setIdProducto(carritoItems.getIdProducto());
        nuevoItem.setIdCarrito(carrito.getIdCarrito());

        return carritoItemsDao.save(nuevoItem);
    }

    @Override
    public List<CarritoItemsConsultaDTO> getItemsByUserAndCarrito(Integer idUser) {
        return carritoItemsDao.findCarritoItemsWithProductDetailsByCarritoId(idUser);
    }


}
