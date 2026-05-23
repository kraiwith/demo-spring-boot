package com.krai.demo_spring_boot.dtos;

import com.krai.demo_spring_boot.enums.MediaTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductMediaRequestDto {
  @NotBlank(message = "Media URL is required")
  private String url;

  @NotNull(message = "Media type is required")
  private MediaTypeEnum type;

  public ProductMediaRequestDto() {}

  public ProductMediaRequestDto(String url, MediaTypeEnum type) {
    this.url = url;
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public MediaTypeEnum getType() {
    return type;
  }
}
