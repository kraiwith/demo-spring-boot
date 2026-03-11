package com.krai.demo_spring_boot.dtos;

import com.krai.demo_spring_boot.enums.MediaTypeEnum;
import jakarta.persistence.Column;

public class ProductMediaRequestDto {
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private MediaTypeEnum type;

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
