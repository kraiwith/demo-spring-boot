package com.krai.demo_spring_boot.controllers;

import com.krai.demo_spring_boot.dtos.CategoryRequestDto;
import com.krai.demo_spring_boot.dtos.CategoryResponseDto;
import com.krai.demo_spring_boot.dtos.ProductRequestDto;
import com.krai.demo_spring_boot.dtos.ProductResponseDto;
import com.krai.demo_spring_boot.dtos.ProductMediaResponseDto;
import com.krai.demo_spring_boot.dtos.ProductMediaUpdateRequestDto;
import com.krai.demo_spring_boot.dtos.StockAdjustmentRequestDto;
import com.krai.demo_spring_boot.dtos.StockResponseDto;
import com.krai.demo_spring_boot.dtos.StockUpdateRequestDto;
import com.krai.demo_spring_boot.services.CategoryService;
import com.krai.demo_spring_boot.services.MediaService;
import com.krai.demo_spring_boot.services.ProductService;
import com.krai.demo_spring_boot.services.StockService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

  private final CategoryService categoryService;
  private final ProductService productService;
  private final StockService stockService;
  private final MediaService mediaService;

  public InventoryController(
      CategoryService categoryService,
      ProductService productService,
      StockService stockService,
      MediaService mediaService) {
    this.categoryService = categoryService;
    this.productService = productService;
    this.stockService = stockService;
    this.mediaService = mediaService;
  }

  @PostMapping("/category")
  public ResponseEntity<CategoryResponseDto> addCategory(
      @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(categoryService.createCategory(categoryRequestDto));
  }

  @PutMapping("/category/{id}")
  public CategoryResponseDto updateCategory(
      @PathVariable Long id, @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
    return categoryService.updateCategory(id, categoryRequestDto);
  }

  @GetMapping("/category")
  public List<CategoryResponseDto> getCategory() {
    return categoryService.getCategories();
  }

  @PostMapping("/product")
  public ResponseEntity<ProductResponseDto> addProduct(
      @Valid @RequestBody ProductRequestDto productRequestDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(productService.createProduct(productRequestDto));
  }

  @GetMapping("/products")
  public List<ProductResponseDto> getProducts() {
    return productService.getProducts();
  }

  @PostMapping(value = "/products/{productId}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<List<ProductMediaResponseDto>> uploadProductMedia(
      @PathVariable Long productId, @RequestParam("files") List<MultipartFile> files) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mediaService.uploadProductMedia(productId, files));
  }

  @GetMapping("/products/{productId}/media")
  public List<ProductMediaResponseDto> getProductMedia(@PathVariable Long productId) {
    return mediaService.getProductMedia(productId);
  }

  @PatchMapping("/media/{mediaId}")
  public ProductMediaResponseDto updateMedia(
      @PathVariable Long mediaId, @Valid @RequestBody ProductMediaUpdateRequestDto request) {
    return mediaService.updateMedia(mediaId, request);
  }

  @DeleteMapping("/media/{mediaId}")
  public ResponseEntity<Void> deleteMedia(@PathVariable Long mediaId) {
    mediaService.deleteMedia(mediaId);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/skus/{skuId}/stock")
  public StockResponseDto adjustStock(
      @PathVariable Long skuId, @Valid @RequestBody StockAdjustmentRequestDto request) {
    return stockService.adjustStock(skuId, request);
  }

  @PutMapping("/skus/{skuId}/stock")
  public StockResponseDto updateStock(
      @PathVariable Long skuId, @Valid @RequestBody StockUpdateRequestDto request) {
    return stockService.updateStock(skuId, request);
  }
}
