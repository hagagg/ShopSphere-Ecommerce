package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.product.ProductRequestDto;
import com.hagag.shopsphere_ecommerce.dto.product.ProductResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(@Valid ProductRequestDto productRequestDto);

    ProductResponseDto getProductById(Long productId);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto updateProduct(Long productId, @Valid ProductRequestDto productRequestDto);

    void deleteProduct(Long productId);
}
