package com.example.demo.service;

import com.example.demo.dto.CategoryDtoIn;
import com.example.demo.dto.CategoryDtoOut;
import com.example.demo.dto.UpdateCategoryDtoIn;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.error.NotFoundException;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDtoOut addCategory(CategoryDtoIn categoryDtoIn) {
        Category category = new Category();
        category.setName(categoryDtoIn.getName());
        category = categoryRepository.save(category);
        return toDto(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        for (Image image : new HashSet<>(category.getImages())) {
            category.removeImage(image);
        }
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDtoOut> getCategories(int page) {
        return categoryRepository.findAll(PageRequest.of(page - 1, 20)).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDtoOut getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return toDto(category);
    }

    @Override
    public CategoryDtoOut updateCategory(UpdateCategoryDtoIn data, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        category.setName(data.getName());
        categoryRepository.save(category);
        return toDto(category);
    }

    private CategoryDtoOut toDto(Category category) {
        return new CategoryDtoOut(category.getId(), category.getName());
    }
}
