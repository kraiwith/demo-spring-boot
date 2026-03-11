package com.krai.demo_spring_boot.models;

import com.krai.demo_spring_boot.enums.MediaTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "media")
public class MediaModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String url;

  private MediaTypeEnum type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_product_id", nullable = true)
  @JsonBackReference
  private ProductModel productModel;

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
}
