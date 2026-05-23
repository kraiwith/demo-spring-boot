package com.krai.demo_spring_boot.services;

import com.krai.demo_spring_boot.dtos.CategoryRequestDto;
import com.krai.demo_spring_boot.dtos.CategoryResponseDto;
import com.krai.demo_spring_boot.enums.CategoryLevelEnum;
import com.krai.demo_spring_boot.enums.StatusEnum;
import com.krai.demo_spring_boot.models.CategoryModel;
import com.krai.demo_spring_boot.repository.CategoryRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Transactional
  public CategoryResponseDto createCategory(CategoryRequestDto request) {
    if (!categoryRepository.findByName(request.getName()).isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name already exists");
    }

    CategoryModel category =
        new CategoryModel(
            request.getName(),
            request.getParentCategoryId(),
            CategoryLevelEnum.LEVEL_1,
            StatusEnum.ACTIVE);

    return toResponse(categoryRepository.save(category));
  }

  @Transactional
  public CategoryResponseDto updateCategory(Long id, CategoryRequestDto request) {
    CategoryModel category =
        categoryRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

    boolean duplicateName =
        categoryRepository.findByName(request.getName()).stream()
            .anyMatch(existingCategory -> !existingCategory.getId().equals(id));
    if (duplicateName) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name already exists");
    }

    category.setName(request.getName());
    category.setParentCategoryId(request.getParentCategoryId());
    category.setLevel(CategoryLevelEnum.LEVEL_1);
    category.setStatus(request.getStatus() == null ? category.getStatus() : request.getStatus());

    return toResponse(category);
  }

  @Transactional(readOnly = true)
  public List<CategoryResponseDto> getCategories() {
    return categoryRepository.findAll().stream().map(this::toResponse).toList();
  }

  private CategoryResponseDto toResponse(CategoryModel category) {
    return new CategoryResponseDto(
        category.getId(),
        category.getName(),
        category.getParentCategoryId(),
        category.getStatus().name());
  }
}
