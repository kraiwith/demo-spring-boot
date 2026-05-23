package com.krai.demo_spring_boot.dtos;

import com.krai.demo_spring_boot.enums.StatusEnum;
import java.time.LocalDateTime;

public class ProductSKUResponseDto {
  private final Long id;
  private final String name;
  private final Double price;
  private final Integer stockQuantity;
  private final StatusEnum status;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public ProductSKUResponseDto(
      Long id,
      String name,
      Double price,
      Integer stockQuantity,
      StatusEnum status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Double getPrice() {
    return price;
  }

  public Integer getStockQuantity() {
    return stockQuantity;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
