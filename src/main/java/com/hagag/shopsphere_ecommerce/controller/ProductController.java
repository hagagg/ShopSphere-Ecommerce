package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.pagination.PaginatedResponseDto;
import com.hagag.shopsphere_ecommerce.dto.product.ProductRequestDto;
import com.hagag.shopsphere_ecommerce.dto.product.ProductResponseDto;
import com.hagag.shopsphere_ecommerce.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDto createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {

        return productService.createProduct (productRequestDto);
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getProductById (@PathVariable Long productId) {

        return productService.getProductById (productId);
    }

    @GetMapping
    public PaginatedResponseDto<ProductResponseDto> getAllProducts (Pageable pageable) {
        return productService.getAllProducts (pageable);
    }

    @PutMapping("/{productId}")
    public ProductResponseDto updateProduct (@PathVariable Long productId,@Valid @RequestBody ProductRequestDto productRequestDto) {

        return productService.updateProduct (productId , productRequestDto);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct (@PathVariable Long productId) {

        productService.deleteProduct (productId);
    }

    @GetMapping("/search")
    public PaginatedResponseDto<ProductResponseDto> searchProductsByName (@RequestParam @NotBlank String name, Pageable pageable) {

        return productService.searchProductsByName (name, pageable);
    }

    @GetMapping("/by-category/{categoryId}")
    public PaginatedResponseDto<ProductResponseDto> getProductsByCategoryId (@PathVariable Long categoryId, Pageable pageable) {

        return productService.getProductsByCategoryId (categoryId, pageable);
    }

    @GetMapping("/price-range")
    public PaginatedResponseDto<ProductResponseDto> getProductsByPriceRange (
            @RequestParam @NotNull BigDecimal minPrice, @RequestParam @NotNull BigDecimal maxPrice, Pageable pageable) {

        return productService.getProductsByPriceRange (minPrice , maxPrice , pageable);
    }

    @GetMapping("/sorted")
    public PaginatedResponseDto<ProductResponseDto> getProductsSortedBy(
            @RequestParam(defaultValue = "price") String field, @RequestParam(defaultValue = "asc") String direction, Pageable pageable) {

        return productService.getProductsSortedBy(field, direction, pageable);
    }

}
