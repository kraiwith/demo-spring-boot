package com.krai.demo_spring_boot.dtos;

import com.krai.demo_spring_boot.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductSKURequestDto {
  @NotBlank(message = "SKU name is required")
  private String name;

  @NotNull(message = "SKU price is required")
  @PositiveOrZero(message = "SKU price cannot be negative")
  private Double price;

  @NotNull(message = "Stock quantity is required")
  @PositiveOrZero(message = "Stock quantity cannot be negative")
  private Integer stockQuantity;

  private Integer reorderLevel;

  private StatusEnum status;

  public ProductSKURequestDto() {}

  public ProductSKURequestDto(
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
