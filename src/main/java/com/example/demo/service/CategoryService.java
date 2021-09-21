package com.example.demo.service;

import com.example.demo.dto.CategoryDtoIn;
import com.example.demo.dto.CategoryDtoOut;
import com.example.demo.dto.UpdateCategoryDtoIn;

import java.util.List;

public interface CategoryService {
    CategoryDtoOut addCategory(CategoryDtoIn categoryDtoIn);

    void deleteCategory(Long categoryId);

    List<CategoryDtoOut> getCategories(int page);

    CategoryDtoOut getCategory(Long id);

    CategoryDtoOut updateCategory(UpdateCategoryDtoIn data, Long id);
}
