package dev.richar.market.controllers;

import dev.richar.market.models.entity.Carrito;
import dev.richar.market.models.services.ICarritoService;
import dev.richar.market.models.services.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @GetMapping
    public ResponseEntity<?> getAllCarritos(){
        return ResponseEntity.ok(carritoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarritoById(@PathVariable Integer id){
        return ResponseEntity.ok(carritoService.findById(id));


    }

    @PostMapping
    public ResponseEntity<?> saveCarrito(@RequestBody Carrito carrito){
        return ResponseEntity.ok(carritoService.save(carrito));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarrito(@PathVariable Integer id, @RequestBody Carrito carrito){
        return ResponseEntity.ok(carritoService.update(carrito, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarrito(@PathVariable Integer id){
        carritoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
