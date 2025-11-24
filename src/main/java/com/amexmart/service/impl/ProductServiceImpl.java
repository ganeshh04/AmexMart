package com.amexmart.service.impl;

import com.amexmart.dto.ProductDto;
import com.amexmart.model.Product;
import com.amexmart.repository.ProductRepository;
import com.amexmart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product product = modelMapper.map(dto, Product.class);
        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }



    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // DO NOT MAP ID
        dto.setId(product.getId());

        modelMapper.map(dto, product);

        Product saved = productRepository.save(product);

        return modelMapper.map(saved, ProductDto.class);
    }


    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return modelMapper.map(p, ProductDto.class);
    }

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(product -> modelMapper.map(product, ProductDto.class));
    }
}
