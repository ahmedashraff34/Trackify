package org.example.inventoryservice.mappers;

import org.example.inventoryservice.dtos.ProductRequest;
import org.example.inventoryservice.dtos.ProductResponse;
import org.example.inventoryservice.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .category(productRequest.getCategory())
                .price(productRequest.getPrice())
                .build();
    }

    public ProductResponse toProductDTO(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .build();
    }
}
