package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.dto.product.ProductRequestDto;
import com.hagag.shopsphere_ecommerce.dto.product.ProductResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {

    ProductResponseDto createProduct(@Valid ProductRequestDto productRequestDto);

    ProductResponseDto getProductById(Long productId);

    PaginatedResponseDto<ProductResponseDto> getAllProducts(Pageable pageable);

    ProductResponseDto updateProduct(Long productId, @Valid ProductRequestDto productRequestDto);

    void deleteProduct(Long productId);

    PaginatedResponseDto<ProductResponseDto> searchProductsByName(@NotBlank String name, Pageable pageable);

    PaginatedResponseDto<ProductResponseDto> getProductsByCategoryId(Long categoryId, Pageable pageable);

    PaginatedResponseDto<ProductResponseDto> getProductsByPriceRange(@NotNull BigDecimal minPrice, @NotNull BigDecimal maxPrice, Pageable pageable);

    PaginatedResponseDto<ProductResponseDto> getProductsSortedBy(String field, String direction, Pageable pageable);
}
