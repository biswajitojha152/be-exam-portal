package com.examportal.repository;

import com.examportal.dto.CategoryDTO;
import com.examportal.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select new com.examportal.dto.CategoryDTO(c.id, c.name) from Category c", nativeQuery = false)
    List<CategoryDTO> getAllCategories();

    Boolean existsByName(String categoryName);


}
