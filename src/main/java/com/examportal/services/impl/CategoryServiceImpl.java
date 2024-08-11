package com.examportal.services.impl;

import com.examportal.dto.CategoryDTO;
import com.examportal.models.Category;
import com.examportal.repository.CategoryRepository;
import com.examportal.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return  categoryRepository.getAllCategories();
    }

    @Override
    public Category saveCategory(CategoryDTO category) {
        if(categoryRepository.existsByName(category.getName())){
            return null;
        }
        Category categoryEntity = new Category();
        categoryEntity.setName(category.getName());
        return categoryRepository.save(categoryEntity);
    }
}
