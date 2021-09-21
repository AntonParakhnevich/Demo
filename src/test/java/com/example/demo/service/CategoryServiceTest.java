package com.example.demo.service;

import com.example.demo.error.NotFoundException;
import com.example.demo.dto.CategoryDtoIn;
import com.example.demo.dto.CategoryDtoOut;
import com.example.demo.dto.UpdateCategoryDtoIn;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    public CategoryServiceImpl categoryService;


    @Test
    public void testAddCategory() {
        CategoryDtoOut first = CategoryDtoOut.builder().id(1L).name("first").build();

        Category category = new Category();
        category.setId(1L);
        category.setName("first");

        CategoryDtoIn categoryDtoIn = new CategoryDtoIn("first");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDtoOut categoryDtoOut = categoryService.addCategory(categoryDtoIn);
        assertEquals(first, categoryDtoOut);
    }

    @Test
    public void testGetCategory() {
        CategoryDtoOut first = new CategoryDtoOut();
        first.setId(1L);
        first.setName("first");

        Category category = new Category();
        category.setName("first");
        category.setId(1L);


        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryDtoOut getCategory = categoryService.getCategory(1L);

        assertEquals(getCategory, first);
    }

    @Test
    public void testGetCategories() {
        List<CategoryDtoOut> categoryDtoOuts = new ArrayList<>();

        CategoryDtoOut first = new CategoryDtoOut();
        first.setId(1L);
        first.setName("first");
        CategoryDtoOut second = new CategoryDtoOut();
        second.setId(2L);
        second.setName("second");

        categoryDtoOuts.add(first);
        categoryDtoOuts.add(second);

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("first");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("second");
        Page<Category> categories = new PageImpl<>(List.of(category1, category2));

        when(categoryRepository.findAll(PageRequest.of(0, 20))).thenReturn(categories);

        List<CategoryDtoOut> getCategories = categoryService.getCategories(1);
        assertEquals(getCategories, categoryDtoOuts);
    }


    @Test
    public void testDeleteCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("first");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        doNothing().when(categoryRepository).delete(category);

        categoryService.deleteCategory(1L);
        verify(categoryRepository, times(1)).delete(eq(category));
    }

    @Test
    public void testDeleteCategoryWhenCategoryIsNotExist() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> categoryService.deleteCategory(1L));
    }

    @Test
    public void testGetCategoryWhenCategoryIsNotExist() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> categoryService.getCategory(1L));
    }

    @Test
    public void testUpdateCategoryWhenCategoryIsNotExist() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> categoryService.updateCategory(new UpdateCategoryDtoIn(), 1L));
    }

}
