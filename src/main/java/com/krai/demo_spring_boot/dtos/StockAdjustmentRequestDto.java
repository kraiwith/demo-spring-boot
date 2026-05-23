package com.krai.demo_spring_boot.dtos;

import jakarta.validation.constraints.NotNull;

public class StockAdjustmentRequestDto {
  @NotNull(message = "Quantity change is required")
  private Integer quantityChange;

  private String reason;

  public StockAdjustmentRequestDto() {}

  public StockAdjustmentRequestDto(Integer quantityChange, String reason) {
    this.quantityChange = quantityChange;
    this.reason = reason;
  }

  public Integer getQuantityChange() {
    return quantityChange;
  }

  public String getReason() {
    return reason;
  }
}
