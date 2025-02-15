package dev.richar.market.controllers;

import dev.richar.market.models.dto.ProductsConsultaDTO;
import dev.richar.market.models.entity.Products;
import dev.richar.market.models.services.IProductsService;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private IProductsService productsService;

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productsService.findAll());
    }

    @GetMapping("/filtros")
    public Page<ProductsConsultaDTO> getProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String categoryName,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        return productsService.getFilteredProducts(categoryId, minPrice, maxPrice, minStock, status, categoryName, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody Products product) {
        return ResponseEntity.ok(productsService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Products product) {
        return ResponseEntity.ok(productsService.update(product, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productsService.delete(id);
        return ResponseEntity.ok().build();
    }









}
