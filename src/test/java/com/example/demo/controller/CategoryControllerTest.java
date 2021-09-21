package com.example.demo.controller;

import com.example.demo.dto.CategoryDtoIn;
import com.example.demo.dto.CategoryDtoOut;
import com.example.demo.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testGetCategories() throws Exception {
        //given
        CategoryDtoOut first = CategoryDtoOut.builder()
                .id(1L)
                .name("first")
                .build();
        CategoryDtoOut second = CategoryDtoOut.builder()
                .id(2L)
                .name("second")
                .build();

        when(categoryService.getCategories(1)).thenReturn(Arrays.asList(first, second));

        //when then
        mvc.perform(MockMvcRequestBuilders.get("/api/category")
                .param("page", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("first"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("second"));
    }

    @Test
    public void testAddCategory() throws Exception {
        //given
        CategoryDtoOut category = CategoryDtoOut.builder().name("first").build();
        CategoryDtoIn categoryDtoIn = new CategoryDtoIn();
        categoryDtoIn.setName("first");
        when(categoryService.addCategory(categoryDtoIn)).thenReturn(category);

        //when then
        mvc.perform(MockMvcRequestBuilders.post("/api/category")
                .contentType(APPLICATION_JSON)
                .content("{\"name\":\"first\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("first"));
    }

    @Test
    public void testGetCategory() throws Exception {
        //given
        CategoryDtoOut categoryDtoOut = CategoryDtoOut.builder().id(1L).name("first").build();

        when(categoryService.getCategory(1L)).thenReturn(categoryDtoOut);

        //when then
        mvc.perform(MockMvcRequestBuilders.get("/api/category/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("first"));
    }


    @Test
    public void testDeleteCategory() throws Exception {
        //given
        doNothing().when(categoryService).deleteCategory(1L);

        //when then
        mvc.perform(MockMvcRequestBuilders.delete("/api/category/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testValidation() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/api/category")
                .contentType(APPLICATION_JSON)
                .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}