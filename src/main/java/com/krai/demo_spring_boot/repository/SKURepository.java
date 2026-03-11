package com.krai.demo_spring_boot.repository;

import com.krai.demo_spring_boot.models.SKUModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SKURepository extends JpaRepository<SKUModel, Long> {}
