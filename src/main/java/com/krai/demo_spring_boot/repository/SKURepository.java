package com.krai.demo_spring_boot.repository;

import com.krai.demo_spring_boot.models.SKUModel;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SKURepository extends JpaRepository<SKUModel, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select sku from SKUModel sku where sku.id = :id")
  Optional<SKUModel> findByIdForUpdate(@Param("id") Long id);
}
