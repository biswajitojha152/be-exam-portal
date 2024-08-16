package com.examportal.services.impl;

import com.examportal.dto.CategoryDTO;
import com.examportal.models.Category;
import com.examportal.repository.CategoryRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList= categoryRepository.findAll();

        return categoryList.stream().map(category -> new CategoryDTO(category.getId(), category.getName(), (long) category.getQuiz().size())).toList();
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
