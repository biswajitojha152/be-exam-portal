package com.examportal.services;

import com.examportal.dto.CategoryDTO;
import com.examportal.models.Category;

import java.util.List;

public interface CategoryService {
    public List<CategoryDTO> getAllCategories();

    public Category saveCategory(CategoryDTO category);
}
