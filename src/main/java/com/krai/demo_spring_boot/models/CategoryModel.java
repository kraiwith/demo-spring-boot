package com.krai.demo_spring_boot.models;

import com.krai.demo_spring_boot.enums.CategoryLevelEnum;
import com.krai.demo_spring_boot.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = true)
  private Long parentCategoryId;

  @Column(nullable = false)
  private CategoryLevelEnum level;

  @Column(nullable = false)
  private StatusEnum status;

  public CategoryModel() {}

  public CategoryModel(String name) {
    this.name = name;
    this.parentCategoryId = null;
    this.status = StatusEnum.ACTIVE;
  }

  public CategoryModel(String name, Long parentCategoryId, CategoryLevelEnum level) {
    this.name = name;
    this.parentCategoryId = parentCategoryId;
    this.level = level;
    this.status = StatusEnum.ACTIVE;
  }

  public CategoryModel(String name, Long parentCategoryId, CategoryLevelEnum level, StatusEnum status) {
    this.name = name;
    this.parentCategoryId = parentCategoryId;
    this.level = level;
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

  public Long getParentCategoryId() {
    return parentCategoryId;
  }

  public void setParentCategoryId(Long parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public CategoryLevelEnum getLevel() {
    return level;
  }

  public void setLevel(CategoryLevelEnum level) {
    this.level = level;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }
}
