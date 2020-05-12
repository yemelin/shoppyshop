package com.example.shoppyshop.dto;

import com.example.shoppyshop.models.Category;
import lombok.Data;

@Data
public class SingleProductDto {
    private Long id;
    private String name;
    private Long price;
    private Category category;
}
