package com.aliyara.smartshop.repository;

import com.aliyara.smartshop.dto.response.ProductResponseDTO;
import com.aliyara.smartshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p where p.stock >= 10 AND p.stock >= 50")
    List<Product> listProducts();
}
