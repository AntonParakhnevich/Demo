package com.example.demo.repository;

import com.example.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "select i from Image i join i.categories c where c.id in (:categoriesIds) order by i.name desc")
    Set<Image> getImagesByCategories(@Param("categoriesIds") List<Long> categoriesIds);
}
