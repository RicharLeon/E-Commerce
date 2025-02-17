package dev.richar.market.models.services;

import dev.richar.market.models.dao.CarritoDao;
import dev.richar.market.models.dao.CarritoItemsDao;
import dev.richar.market.models.dao.OurUsersDao;
import dev.richar.market.models.entity.Carrito;
import dev.richar.market.models.entity.OurUsers;
import dev.richar.market.utils.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements ICarritoService {

    @Autowired
    private final CarritoDao carritoDao;
    @Autowired
    private final OurUsersDao ourUsersDao;
    @Autowired
    private final CarritoItemsDao carritoItemsService;



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


    private boolean aplicarDescuentoHorasParametrizadas(Integer id) {
        Carrito carrito1 = carritoDao.findById(id).orElseThrow(
                () -> new RuntimeException(MessageFormat.format("Carrito with id {0} not found", id))
        );

        LocalDateTime createdAt = carrito1.getCreatedAt();
        LocalDateTime horaActual = LocalDateTime.now();

        // Calcula la diferencia entre createdAt y updatedAt
        Duration duration = Duration.between(createdAt, horaActual);

        // Verifica si han pasado 12 horas
        if (duration.toHours() <= Constantes.TIEMPO_DESCUENTO) {
            return true;
        }
        return false;

    }

    private boolean aplicarDescuentoPedidoAleatorio(Carrito carrito) {
        return carritoItemsService.findCarritoItemsWithProductDetailsByCarritoId(carrito.getIdUsuario())
                .stream()
                .anyMatch(carritoItem -> carritoItem.getIsRandom());
    }

    private boolean aplicarDescuentoClienteFrecuente(Carrito carrito) {
        List<OurUsers> data = ourUsersDao.findFrequentUsers(carrito.getIdUsuario());
            if (!data.isEmpty()) return true;
        return false;
    }

    @Override
    public double validarSiCuentaConDescuentos(Carrito carrito) {
        double totalDescuento = 0.0;

        if (aplicarDescuentoHorasParametrizadas(carrito.getIdCarrito())){
            totalDescuento += 0.10;
            if (aplicarDescuentoPedidoAleatorio(carrito)){
                totalDescuento += 0.50;
            }if (aplicarDescuentoClienteFrecuente(carrito)){
                totalDescuento += 0.10;
            }
        }

        return totalDescuento * 100;

    }


}
