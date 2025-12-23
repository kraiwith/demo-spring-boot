package com.krai.demo_spring_boot.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(length = 1000)
  private String description;

  private List<Long> skus;

  private Long categoryId;

  private List<Long> medias;

  private Status status;

  public ProductModel() {}

  public ProductModel(
      String name,
      String description,
      List<Long> skus,
      Long categoryId,
      List<Long> medias,
      Status status) {
    this.name = name;
    this.description = description;
    this.skus = skus;
    this.categoryId = categoryId;
    this.medias = medias;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Long> getSKUs() {
    return skus;
  }

  public void setSKUs(List<Long> skus) {
    this.skus = skus;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public List<Long> getMedias() {
    return medias;
  }

  public void setMedias(List<Long> medias) {
    this.medias = medias;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
