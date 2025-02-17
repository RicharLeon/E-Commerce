package dev.richar.market.models.services;

import dev.richar.market.models.dao.PayDao;
import dev.richar.market.models.entity.Pay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements IPayService {

    @Autowired
    private final PayDao payService;

    @Override
    public List<Pay> findAll() {
        return payService.findAll();
    }

    @Override
    public Pay findById(Integer id) {
        return payService.findById(id).orElseThrow(
                () -> new RuntimeException(MessageFormat
                        .format("Pay with id {0} not found", id))
        );
    }

    @Override
    public Pay save(Pay pay) {
        return payService.save(pay);
    }

    @Override
    public void delete(Integer id) {
        payService.findById(id).orElseThrow(
                () -> new RuntimeException(MessageFormat
                        .format("Pay with id {0} not found", id))
        );
        payService.deleteById(id);
    }

    @Override
    public Pay update(Pay pay, Integer id) {
        Pay payToUpdate = payService.findById(id).orElseThrow(
                () -> new RuntimeException(MessageFormat
                        .format("Pay with id {0} not found", id))
        );

        payToUpdate.setEstadoPago(pay.getEstadoPago());
        payToUpdate.setMonto(pay.getMonto());
        payToUpdate.setCarrito(pay.getCarrito());
        payToUpdate.setFechaPago(pay.getFechaPago());

        return payService.save(payToUpdate);
    }



//    public void actualizarItems() {
//        payService.findByIdAndEstadoPago(1, "PENDIENTE").orElseThrow(
//                () -> new RuntimeException(MessageFormat
//                        .format("Pay with id {0} not found", 1))
//        );
//
//
//    }
}
