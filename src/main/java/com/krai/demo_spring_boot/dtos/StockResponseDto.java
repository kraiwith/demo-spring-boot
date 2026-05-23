package com.krai.demo_spring_boot.dtos;

import java.time.LocalDateTime;

public class StockResponseDto {
  private final Long skuId;
  private final String skuName;
  private final Integer stockQuantity;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public StockResponseDto(
      Long skuId,
      String skuName,
      Integer stockQuantity,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.skuId = skuId;
    this.skuName = skuName;
    this.stockQuantity = stockQuantity;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getSkuId() {
    return skuId;
  }

  public String getSkuName() {
    return skuName;
  }

  public Integer getStockQuantity() {
    return stockQuantity;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
