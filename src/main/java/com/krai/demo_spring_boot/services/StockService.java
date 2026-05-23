package com.krai.demo_spring_boot.services;

import com.krai.demo_spring_boot.dtos.StockAdjustmentRequestDto;
import com.krai.demo_spring_boot.dtos.StockResponseDto;
import com.krai.demo_spring_boot.dtos.StockUpdateRequestDto;
import com.krai.demo_spring_boot.models.SKUModel;
import com.krai.demo_spring_boot.repository.SKURepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StockService {
  private final SKURepository skuRepository;

  public StockService(SKURepository skuRepository) {
    this.skuRepository = skuRepository;
  }

  @Transactional
  public StockResponseDto adjustStock(Long skuId, StockAdjustmentRequestDto request) {
    SKUModel sku = findSkuForUpdate(skuId);
    int newQuantity = sku.getStockQuantity() + request.getQuantityChange();
    if (newQuantity < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock quantity cannot be negative");
    }
    sku.setStockQuantity(newQuantity);
    return toResponse(skuRepository.saveAndFlush(sku));
  }

  @Transactional
  public StockResponseDto updateStock(Long skuId, StockUpdateRequestDto request) {
    SKUModel sku = findSkuForUpdate(skuId);
    sku.setStockQuantity(request.getStockQuantity());
    return toResponse(skuRepository.saveAndFlush(sku));
  }

  private SKUModel findSkuForUpdate(Long skuId) {
    return skuRepository
        .findByIdForUpdate(skuId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SKU not found"));
  }

  private StockResponseDto toResponse(SKUModel sku) {
    return new StockResponseDto(
        sku.getId(), sku.getName(), sku.getStockQuantity(), sku.getCreatedAt(), sku.getUpdatedAt());
  }
}
