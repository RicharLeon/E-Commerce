package dev.richar.market.models.services;

import dev.richar.market.models.dao.CarritoDao;
import dev.richar.market.models.entity.Carrito;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements ICarritoService {

    @Autowired
    private final CarritoDao carritoDao;


    @Override
    public List<Carrito> findAll() {
        return carritoDao.findAll();
    }

    @Override
    public Carrito findById(Integer id) {
        return carritoDao.findByIdUsuario(id).orElseThrow(
                () -> new RuntimeException(MessageFormat
                        .format("Carrito with id {0} not found", id))
        );
    }

    @Override
    public Optional<Carrito> findByIdOptional(Integer id) {
        return carritoDao.findByIdUsuario(id);
    }

    @Override
    public Carrito save(Carrito carrito) {
        return carritoDao.save(carrito);
    }

    @Override
    public void delete(Integer id) {
        carritoDao.findById(id).orElseThrow(
                () -> new RuntimeException(MessageFormat
                        .format("Carrito with id {0} not found", id))
        );
        carritoDao.deleteById(id);
    }

    @Override
    public Carrito update(Carrito carrito, Integer id) {
        Carrito carrito1 = carritoDao.findById(id).orElseThrow(
                () -> new RuntimeException(MessageFormat
                        .format("Carrito with id {0} not found", id))
        );

        carrito1.setIdCarrito(carrito.getIdCarrito());
        carrito1.setIdUsuario(carrito.getIdUsuario());

        return carritoDao.save(carrito);

    }
}
