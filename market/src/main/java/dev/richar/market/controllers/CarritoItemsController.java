package dev.richar.market.controllers;

import dev.richar.market.models.entity.CarritoItems;
import dev.richar.market.models.services.ICarritoItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/carrito-items")
public class CarritoItemsController {

    @Autowired
    private ICarritoItemsService carritoItemsService;

    @GetMapping
    public ResponseEntity<?> getAllCarritoItems(){
        return ResponseEntity.ok(carritoItemsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarritoItemsById(Integer id){
        return ResponseEntity.ok(carritoItemsService.findById(id));
    }
    @PostMapping
    public ResponseEntity<?> saveCarritoItems(@RequestBody CarritoItems carritoItems){
        return ResponseEntity.ok(carritoItemsService.save(carritoItems));
    }

    @PutMapping("/{idCarritoItem}")
    public ResponseEntity<?> updateCarritoItems(@PathVariable Integer idCarritoItem,
                                                @RequestBody CarritoItems carritoItems
                                                ){
        return ResponseEntity.ok(carritoItemsService.update(carritoItems, idCarritoItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarritoItems(@PathVariable Integer id){
        carritoItemsService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-item/{idUser}")
    public ResponseEntity<?> addItemToUserCart(@PathVariable Integer idUser, @RequestBody CarritoItems carritoItems){
        return ResponseEntity.ok(carritoItemsService.addItemToUserCart(idUser, carritoItems));
    }

    @GetMapping("/items-by-user/{idUser}")
    public ResponseEntity<?> getItemsByUserAndCarrito(@PathVariable Integer idUser){
        return ResponseEntity.ok(carritoItemsService.getItemsByUserAndCarrito(idUser));
    }


}
