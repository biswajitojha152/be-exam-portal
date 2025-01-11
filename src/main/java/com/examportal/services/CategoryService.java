package com.examportal.services;

import com.examportal.dto.*;
import com.examportal.models.Category;

import java.util.List;

public interface CategoryService {
    ResponseDTO<List<CategoryDTO>> getAllCategories();

    ResponseDTO<Category> saveCategory(CategoryDTO category);

    ResponseDTO<Category> updateCategory(CategoryDTO categoryDTO);

    ResponseDTO<List<Category>> updateCategoriesStatus(UpdateCategoriesStatusDTO updateCategoriesStatusDTO);

    EntityUpdateTrailDTO<CategoryDTO> getCategoryUpdateTrailDTO(Integer entityId);

    List<EntitiesStatusUpdateTrailDTO<CategoryDTO>> getCategoriesStatusUpdateTrailDTO();
}
