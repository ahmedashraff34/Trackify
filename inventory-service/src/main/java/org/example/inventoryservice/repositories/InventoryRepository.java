package org.example.inventoryservice.repositories;

import org.example.inventoryservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository <Product, Long> {
}
