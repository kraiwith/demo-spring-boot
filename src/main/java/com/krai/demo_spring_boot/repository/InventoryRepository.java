package com.krai.demo_spring_boot.repository;

import com.krai.demo_spring_boot.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<ProductModel, Long> {}
