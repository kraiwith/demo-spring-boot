package com.krai.demo_spring_boot.controllers;

import com.krai.demo_spring_boot.dtos.CategoryRequestDto;
import com.krai.demo_spring_boot.dtos.CategoryResponseDto;
import com.krai.demo_spring_boot.dtos.ProductRequestDto;
import com.krai.demo_spring_boot.enums.CategoryLevelEnum;
import com.krai.demo_spring_boot.enums.StatusEnum;
import com.krai.demo_spring_boot.models.CategoryModel;
import com.krai.demo_spring_boot.models.MediaModel;
import com.krai.demo_spring_boot.models.ProductModel;
import com.krai.demo_spring_boot.models.SKUModel;
import com.krai.demo_spring_boot.repository.CategoryRepository;
import com.krai.demo_spring_boot.repository.MediaRepository;
import com.krai.demo_spring_boot.repository.ProductRepository;
import com.krai.demo_spring_boot.repository.SKURepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

  private final ProductRepository productRepository;
  private final SKURepository skuRepository;
  private final CategoryRepository categoryRepository;
  private final MediaRepository mediaRepository;

  public InventoryController(
      ProductRepository productRepository,
      SKURepository skuRepository,
      CategoryRepository categoryRepository,
      MediaRepository mediaRepository) {
    this.productRepository = productRepository;
    this.skuRepository = skuRepository;
    this.categoryRepository = categoryRepository;
    this.mediaRepository = mediaRepository;
  }

  @PostMapping("/category")
  public String addCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
    CategoryModel categoryModel =
        new CategoryModel(
            categoryRequestDto.getName(),
            categoryRequestDto.getParentCategoryId(),
            CategoryLevelEnum.LEVEL_1,
            StatusEnum.ACTIVE);
    categoryModel.setId(UUID.randomUUID().getMostSignificantBits());
    categoryRepository.save(categoryModel);
    return "Category added successfully";
  }

  @PutMapping("/category/{id}")
  public String updateCategory(
      @PathVariable String id, @RequestBody CategoryRequestDto categoryRequestDto) {
    CategoryModel updateCategoryDto =
        new CategoryModel(
            categoryRequestDto.getName(),
            categoryRequestDto.getParentCategoryId(),
            CategoryLevelEnum.LEVEL_1,
            categoryRequestDto.getStatus());
    Long newCategoryId = Long.parseLong(id);
    if (!categoryRepository.existsById(newCategoryId)) {
      return "Category not found";
    }
    if (!categoryRepository.findByName(categoryRequestDto.getName()).isEmpty()) {
      return "Category with the same name already exists";
    }
    updateCategoryDto.setId(newCategoryId);
    categoryRepository.save(updateCategoryDto);

    return "Category updated successfully";
  }

  @GetMapping("/category")
  public List<CategoryResponseDto> getCategory() {
    return categoryRepository.findAll().stream()
        .map(
            categoryModel ->
                new CategoryResponseDto(
                    categoryModel.getId(),
                    categoryModel.getName(),
                    categoryModel.getParentCategoryId(),
                    categoryModel.getStatus().name()))
        .toList();
  }

  @PostMapping("/product")
  @Transactional
  public String addProduct(@RequestBody ProductRequestDto productRequestDto) {
    ProductModel product = new ProductModel();
    product.setName(productRequestDto.getName());
    product.setDescription(productRequestDto.getDescription());

    if (productRequestDto.getSkus() != null) {
      List<SKUModel> skus =
          productRequestDto.getSkus().stream()
              .map(
                  skuDto -> {
                    SKUModel sku =
                        new SKUModel(
                            skuDto.getName(), skuDto.getPrice(), skuDto.getStockQuantity());
                    sku.setProductModel(product);
                    return sku;
                  })
              .toList();
      product.setSKUs(skus);
    }

    if (productRequestDto.getMedias() != null) {
      List<MediaModel> medias =
          productRequestDto.getMedias().stream()
              .map(
                  mediaDto -> {
                    MediaModel mediaModel = new MediaModel(mediaDto.getUrl(), mediaDto.getType());
                    mediaModel.setProduct(product);
                    return mediaModel;
                  })
              .toList();
      product.setMedias(medias);
    }

    productRepository.save(product);
    return "Product added successfully";
  }

  @GetMapping("/products")
  public List<ProductModel> getProducts() {
    return productRepository.findAll();
  }
}
