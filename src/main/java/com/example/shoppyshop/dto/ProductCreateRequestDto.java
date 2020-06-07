package com.example.shoppyshop.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
//for some reason application.properties CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES doesn't work so need this
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductCreateRequestDto {
    @NotNull
    private Long categoryId;
    private String name;
    private Long price;


}
