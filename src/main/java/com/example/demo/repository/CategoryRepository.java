package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
