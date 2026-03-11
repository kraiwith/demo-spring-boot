package com.krai.demo_spring_boot.dtos;

import com.krai.demo_spring_boot.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class ProductRequestDto {
  @NotBlank(message = "Product name is required")
  private String name;

  private String description;

  @NotBlank(message = "At least one SKU is required")
  private List<ProductSKURequestDto> skus;

  private String categoryName;

  private Long parentCategoryId;

  private List<ProductMediaRequestDto> medias;

  private StatusEnum status;

  public ProductRequestDto() {}

  public ProductRequestDto(
      String name,
      String description,
      List<ProductSKURequestDto> skus,
      String categoryName,
      List<ProductMediaRequestDto> medias) {
    this.name = name;
    this.description = description;
    this.skus = skus;
    this.categoryName = categoryName;
    this.medias = medias;
  }

  public ProductRequestDto(
      String name,
      String description,
      List<ProductSKURequestDto> skus,
      String categoryName,
      Long parentCategoryId,
      List<ProductMediaRequestDto> medias,
      StatusEnum status) {
    this.name = name;
    this.description = description;
    this.skus = skus;
    this.categoryName = categoryName;
    this.parentCategoryId = parentCategoryId;
    this.medias = medias;
    this.status = status;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<ProductSKURequestDto> getSkus() {
    return skus;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public Long getParentCategoryId() {
    return parentCategoryId;
  }

  public List<ProductMediaRequestDto> getMedias() {
    return medias;
  }

  public StatusEnum getStatus() {
    return status;
  }
}
