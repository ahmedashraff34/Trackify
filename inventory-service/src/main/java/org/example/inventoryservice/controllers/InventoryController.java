package org.example.inventoryservice.controllers;

import lombok.AllArgsConstructor;
import org.example.inventoryservice.models.Product;
import org.example.inventoryservice.services.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> list = inventoryService.getAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = inventoryService.addProduct(product);
        return ResponseEntity.status(201).body(newProduct);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Void> deleteProduct(@RequestParam long productId) {
        inventoryService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestParam long productId,@RequestBody Product product) {
        return ResponseEntity.ok(inventoryService.updateProduct(productId, product));
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(inventoryService.getProduct(id));
    }

}
