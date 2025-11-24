package com.amexmart.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 255)
    private String name;

    @Size(max = 1000)
    private String description;

    @Min(value = 0, message = "Price must be >= 0")
    private double price;

    @Min(value = 0, message = "Stock must be >= 0")
    private int stock;

    @Size(max = 255)
    private String category;

    @Size(max = 1000)
    private String imageUrl;
}
