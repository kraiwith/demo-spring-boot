package com.krai.demo_spring_boot.dtos;

import com.krai.demo_spring_boot.enums.StatusEnum;
import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDto {
  private final Long id;
  private final String name;
  private final String description;
  private final StatusEnum status;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final List<ProductSKUResponseDto> skus;
  private final List<ProductMediaResponseDto> medias;

  public ProductResponseDto(
      Long id,
      String name,
      String description,
      StatusEnum status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      List<ProductSKUResponseDto> skus,
      List<ProductMediaResponseDto> medias) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.skus = skus;
    this.medias = medias;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
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

  public List<ProductSKUResponseDto> getSkus() {
    return skus;
  }

  public List<ProductMediaResponseDto> getMedias() {
    return medias;
  }
}
