package com.hagag.shopsphere_ecommerce.service;

import com.hagag.shopsphere_ecommerce.dto.category.CategoryRequestDto;
import com.hagag.shopsphere_ecommerce.dto.category.CategoryResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto getCategoryById(long categoryId);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto updateCategory(Long categoryId, @Valid CategoryRequestDto categoryRequestDto);

    void deleteCategory(Long categoryId);

    List<CategoryResponseDto> searchCategoriesByName(@NotBlank String name);
}
