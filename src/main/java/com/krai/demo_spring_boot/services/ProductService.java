package com.krai.demo_spring_boot.services;

import com.krai.demo_spring_boot.dtos.ProductMediaResponseDto;
import com.krai.demo_spring_boot.dtos.ProductRequestDto;
import com.krai.demo_spring_boot.dtos.ProductResponseDto;
import com.krai.demo_spring_boot.dtos.ProductSKUResponseDto;
import com.krai.demo_spring_boot.models.MediaModel;
import com.krai.demo_spring_boot.models.ProductModel;
import com.krai.demo_spring_boot.models.SKUModel;
import com.krai.demo_spring_boot.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional
  public ProductResponseDto createProduct(ProductRequestDto request) {
    ProductModel product = new ProductModel();
    product.setName(request.getName());
    product.setDescription(request.getDescription());
    if (request.getStatus() != null) {
      product.setStatus(request.getStatus());
    }

    List<SKUModel> skus =
        request.getSkus().stream()
            .map(
                skuDto -> {
                  SKUModel sku =
                      new SKUModel(
                          skuDto.getName(), skuDto.getPrice(), skuDto.getStockQuantity());
                  if (skuDto.getStatus() != null) {
                    sku.setStatus(skuDto.getStatus());
                  }
                  sku.setProductModel(product);
                  return sku;
                })
            .toList();
    product.setSKUs(skus);

    if (request.getMedias() != null) {
      List<MediaModel> medias =
          request.getMedias().stream()
              .map(
                  mediaDto -> {
                    MediaModel media = new MediaModel(mediaDto.getUrl(), mediaDto.getType());
                    media.setProduct(product);
                    return media;
                  })
              .toList();
      product.setMedias(medias);
    }

    return toResponse(productRepository.saveAndFlush(product));
  }

  @Transactional(readOnly = true)
  public List<ProductResponseDto> getProducts() {
    return productRepository.findAll().stream().map(this::toResponse).toList();
  }

  ProductResponseDto toResponse(ProductModel product) {
    List<ProductSKUResponseDto> skus =
        product.getSKUs() == null
            ? List.of()
            : product.getSKUs().stream()
                .map(
                    sku ->
                        new ProductSKUResponseDto(
                            sku.getId(),
                            sku.getName(),
                            sku.getPrice(),
                            sku.getStockQuantity(),
                            sku.getStatus(),
                            sku.getCreatedAt(),
                            sku.getUpdatedAt()))
                .toList();

    List<ProductMediaResponseDto> medias =
        product.getMedias() == null
            ? List.of()
            : product.getMedias().stream()
                .map(
                    media ->
                        new ProductMediaResponseDto(
                            media.getId(),
                            media.getUrl(),
                            media.getType(),
                            media.getOriginalFileName(),
                            media.getContentType(),
                            media.getSize(),
                            media.getAltText(),
                            media.getSortOrder(),
                            media.getCreatedAt(),
                            media.getUpdatedAt()))
                .toList();

    return new ProductResponseDto(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getStatus(),
        product.getCreatedAt(),
        product.getUpdatedAt(),
        skus,
        medias);
  }
}
