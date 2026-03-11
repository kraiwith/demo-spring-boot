package com.krai.demo_spring_boot.models;

import com.krai.demo_spring_boot.enums.StatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

  @OneToMany(mappedBy = "productModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MediaModel> medias;
  
  private StatusEnum status = StatusEnum.ACTIVE;

  @OneToMany(mappedBy = "productModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SKUModel> skus;

  public ProductModel() {}

  public ProductModel(String name, String description, List<SKUModel> skus, List<MediaModel> medias) {
    this.name = name;
    this.description = description;
    this.skus = skus;
    this.medias = medias;
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

  public List<SKUModel> getSKUs() {
    return skus;
  }

  public void setSKUs(List<SKUModel> skus) {
    this.skus = skus;
  }

  public List<MediaModel> getMedias() {
    return medias;
  }

  public void setMedias(List<MediaModel> medias) {
    this.medias = medias;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }
}
