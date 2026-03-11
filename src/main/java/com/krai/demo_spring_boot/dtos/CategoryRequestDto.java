package com.krai.demo_spring_boot.dtos;

import com.krai.demo_spring_boot.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {
  @NotBlank(message = "Category name is required")
  private String name;

  private Long parentCategoryId;

  private StatusEnum status = StatusEnum.ACTIVE;

  public CategoryRequestDto() {}

  public CategoryRequestDto(String name, Long parentCategory) {
    this.name = name;
    this.parentCategoryId = parentCategory;
  }

  public CategoryRequestDto(String name, Long parentCategory, StatusEnum status) {
    this.name = name;
    this.parentCategoryId = parentCategory;
    this.status = status;
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

  public StatusEnum getStatus() {
    return status;
  }
}
