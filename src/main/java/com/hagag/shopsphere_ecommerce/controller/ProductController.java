package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.product.ProductRequestDto;
import com.hagag.shopsphere_ecommerce.dto.product.ProductResponseDto;
import com.hagag.shopsphere_ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<ProductResponseDto> getAllProducts () {
        return productService.getAllProducts ();
    }

    @PutMapping("/{productId}")
    public ProductResponseDto updateProduct (@PathVariable Long productId,@Valid @RequestBody ProductRequestDto productRequestDto) {

        return productService.updateProduct (productId , productRequestDto);
    }
    @DeleteMapping("/{productId}")
    public void deleteProduct (@PathVariable Long productId) {

        productService.deleteProduct (productId);
    }


}
