package com.amexmart.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal price;

    private BigDecimal discountedPrice;

    @NotNull
    private Integer stockQuantity;

    private String imageUrl;

    private String brand;

    @NotNull
    private Long categoryId;

    private Double averageRating;

    private Integer reviewCount;

    private boolean active;
}
