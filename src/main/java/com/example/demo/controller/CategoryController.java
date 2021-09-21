package com.example.demo.controller;

import com.example.demo.dto.CategoryDtoIn;
import com.example.demo.dto.CategoryDtoOut;
import com.example.demo.dto.UpdateCategoryDtoIn;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "{id}")
    public CategoryDtoOut getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @GetMapping
    public List<CategoryDtoOut> getCategories(@RequestParam int page) {
        return categoryService.getCategories(page);
    }

    @PostMapping
    public CategoryDtoOut addCategory(@Valid @RequestBody CategoryDtoIn categoryDtoIn) {
        return categoryService.addCategory(categoryDtoIn);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public CategoryDtoOut updateCategory(@PathVariable Long id,
                                         @Valid @RequestBody UpdateCategoryDtoIn data) {
        return categoryService.updateCategory(data, id);
    }

}
