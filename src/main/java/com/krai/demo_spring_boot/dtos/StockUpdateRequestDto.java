package com.krai.demo_spring_boot.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class StockUpdateRequestDto {
  @NotNull(message = "Stock quantity is required")
  @PositiveOrZero(message = "Stock quantity cannot be negative")
  private Integer stockQuantity;

  private String reason;

  public StockUpdateRequestDto() {}

  public StockUpdateRequestDto(Integer stockQuantity, String reason) {
    this.stockQuantity = stockQuantity;
    this.reason = reason;
  }

  public Integer getStockQuantity() {
    return stockQuantity;
  }

  public String getReason() {
    return reason;
  }
}
