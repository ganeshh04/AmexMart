package com.amexmart.service;

import com.amexmart.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDto createProduct(ProductDto dto);

    ProductDto updateProduct(Long id, ProductDto dto);

    void deleteProduct(Long id);

    ProductDto getProductById(Long id);

    Page<ProductDto> getAllProducts(Pageable pageable);
}
