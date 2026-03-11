package com.krai.demo_spring_boot.dtos;

import com.krai.demo_spring_boot.enums.StatusEnum;

public class ProductSKURequestDto {
  private String name;

  private Double price;

  private Integer stockQuantity;

  private Integer reorderLevel;

  private StatusEnum status;

  ProductSKURequestDto(
      String name, Double price, Integer stockQuantity, Integer reorderLevel, StatusEnum status) {
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.reorderLevel = reorderLevel;
    this.status = status;
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

  public Integer getReorderLevel() {
    return reorderLevel;
  }

  public StatusEnum getStatus() {
    return status;
  }
}
