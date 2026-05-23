package com.krai.demo_spring_boot.dtos;

import com.krai.demo_spring_boot.enums.MediaTypeEnum;
import java.time.LocalDateTime;

public class ProductMediaResponseDto {
  private final Long id;
  private final String url;
  private final MediaTypeEnum type;
  private final String originalFileName;
  private final String contentType;
  private final Long size;
  private final String altText;
  private final Integer sortOrder;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public ProductMediaResponseDto(Long id, String url, MediaTypeEnum type) {
    this(id, url, type, null, null, null, null, 0, null, null);
  }

  public ProductMediaResponseDto(
      Long id,
      String url,
      MediaTypeEnum type,
      String originalFileName,
      String contentType,
      Long size,
      String altText,
      Integer sortOrder,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.url = url;
    this.type = type;
    this.originalFileName = originalFileName;
    this.contentType = contentType;
    this.size = size;
    this.altText = altText;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public MediaTypeEnum getType() {
    return type;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public String getContentType() {
    return contentType;
  }

  public Long getSize() {
    return size;
  }

  public String getAltText() {
    return altText;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
