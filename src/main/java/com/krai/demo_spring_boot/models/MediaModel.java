package com.krai.demo_spring_boot.models;

import com.krai.demo_spring_boot.enums.MediaTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "media")
public class MediaModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String url;

  private String storageKey;

  private String originalFileName;

  private String contentType;

  private Long size;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MediaTypeEnum type;

  private String altText;

  private Integer sortOrder = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_product_id", nullable = true)
  @JsonBackReference
  private ProductModel productModel;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public MediaModel() {}

  public MediaModel(String url, MediaTypeEnum type) {
    this.url = url;
    this.type = type;
  }

  public MediaModel(String url, MediaTypeEnum type, ProductModel product) {
    this.url = url;
    this.type = type;
    this.productModel = product;
  }

  public Long getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getStorageKey() {
    return storageKey;
  }

  public void setStorageKey(String storageKey) {
    this.storageKey = storageKey;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public MediaTypeEnum getType() {
    return type;
  }

  public void setType(MediaTypeEnum type) {
    this.type = type;
  }

  public ProductModel getProduct() {
    return productModel;
  }

  public void setProduct(ProductModel product) {
    this.productModel = product;
  }

  public String getAltText() {
    return altText;
  }

  public void setAltText(String altText) {
    this.altText = altText;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
