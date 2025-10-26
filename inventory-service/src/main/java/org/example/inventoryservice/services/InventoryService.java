package org.example.inventoryservice.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.models.Product;
import org.example.inventoryservice.repositories.InventoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<Product> getAll(){
        return inventoryRepository.findAll();
    }

    public Product addProduct(Product product){
        try{
            return inventoryRepository.save(product);
        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Product already exists");
        }
    }

    public void deleteProduct(long productId){
        try{
            inventoryRepository.deleteById(productId);
        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Product does not exist");
        }
    }

    public Product updateProduct(long productId, Product product){
        Product p = inventoryRepository.findById(productId).orElseThrow(()-> new IllegalArgumentException("Product does not exist"));
        try{
            p.setName(product.getName());
            p.setCategory(product.getCategory());
            p.setPrice(product.getPrice());
            return inventoryRepository.save(p);
        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Product name already exist");
        }
    }

    public Product getProduct(long productId){
        return inventoryRepository.findById(productId).orElseThrow(()-> new IllegalArgumentException("Product does not exist"));
    }



}
