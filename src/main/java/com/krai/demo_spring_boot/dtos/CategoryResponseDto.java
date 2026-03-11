package com.krai.demo_spring_boot.dtos;

public class CategoryResponseDto {
    Long id;

    String name;

    Long parentCategoryId;

    String status;

    public CategoryResponseDto(Long id, String name, Long parentCategoryId, String status) {
        this.id = id;
        this.name = name;
        this.parentCategoryId = parentCategoryId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public String getStatus() {
        return status;
    }
}
