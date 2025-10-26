package org.example.inventoryservice.services;

import lombok.AllArgsConstructor;
import org.example.inventoryservice.dtos.ProductRequest;
import org.example.inventoryservice.dtos.ProductResponse;
import org.example.inventoryservice.mappers.ProductMapper;
import org.example.inventoryservice.models.Product;
import org.example.inventoryservice.repositories.InventoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAll(){
        return inventoryRepository.findAll().stream().map(productMapper::toProductDTO).toList();
    }

    @CachePut(value="product",key="#result.id")
    public ProductResponse addProduct(ProductRequest request){
        Product product = productMapper.toProduct(request);
        try{
            inventoryRepository.save(product);
            return productMapper.toProductDTO(product);
        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Product already exists");
        }
    }

    @CacheEvict(value="product", key="#productId")
    public void deleteProduct(long productId){
        try{
            inventoryRepository.deleteById(productId);
        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Product does not exist");
        }
    }

    @CachePut(value="product", key="#result.id")
    public ProductResponse updateProduct(long productId, ProductRequest product){
        Product p = inventoryRepository.findById(productId).orElseThrow(()-> new IllegalArgumentException("Product does not exist"));
        try{
            p.setName(product.getName());
            p.setCategory(product.getCategory());
            p.setPrice(product.getPrice());
            inventoryRepository.save(p);
            return productMapper.toProductDTO(p);
        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Product name already exist");
        }
    }

    @Cacheable(value="product", key="#productId")
    public ProductResponse getProduct(long productId){
        System.out.println("Accessing database");
        return productMapper.toProductDTO(inventoryRepository.findById(productId).orElseThrow(()-> new IllegalArgumentException("Product does not exist")));
    }

}
