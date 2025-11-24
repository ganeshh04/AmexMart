package com.amexmart.controller;

import com.amexmart.dto.ProductDto;
import com.amexmart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    // PUBLIC - pagination supported
    @GetMapping
    public Page<ProductDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.getAllProducts(PageRequest.of(page, size));
    }

    // PUBLIC - get by id
    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductDto create(@Valid @RequestBody ProductDto dto) {
        return productService.createProduct(dto);
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        return productService.updateProduct(id, dto);
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
