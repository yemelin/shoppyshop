package com.example.shoppyshop.dto;

import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private Long price;
    private CategoryResponseDto category;
}
