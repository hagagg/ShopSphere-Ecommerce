package com.hagag.shopsphere_ecommerce.controller;

import com.hagag.shopsphere_ecommerce.dto.category.CategoryRequestDto;
import com.hagag.shopsphere_ecommerce.dto.category.CategoryResponseDto;
import com.hagag.shopsphere_ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponseDto createCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {

        return categoryService.createCategory(categoryRequestDto);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponseDto getCategoryById(@PathVariable long categoryId) {

        return categoryService.getCategoryById (categoryId);
    }

    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {

        return categoryService.getAllCategories ();
    }

    @PutMapping("/{categoryId}")
    public CategoryResponseDto updateCategory(@PathVariable Long categoryId , @RequestBody @Valid CategoryRequestDto categoryRequestDto) {

        return categoryService.updateCategory (categoryId , categoryRequestDto);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory (categoryId);
    }

    @GetMapping("/search")
    public List<CategoryResponseDto> searchCategoriesByName (@RequestParam @NotBlank String name) {

        return categoryService.searchCategoriesByName (name);
    }
}

