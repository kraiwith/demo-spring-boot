package com.krai.demo_spring_boot.dtos;

import jakarta.validation.constraints.PositiveOrZero;

public class ProductMediaUpdateRequestDto {
  private String altText;

  @PositiveOrZero(message = "Sort order cannot be negative")
  private Integer sortOrder;

  public ProductMediaUpdateRequestDto() {}

  public ProductMediaUpdateRequestDto(String altText, Integer sortOrder) {
    this.altText = altText;
    this.sortOrder = sortOrder;
  }

  public String getAltText() {
    return altText;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }
}
