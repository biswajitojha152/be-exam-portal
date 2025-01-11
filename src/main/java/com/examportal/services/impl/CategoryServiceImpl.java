package com.examportal.services.impl;

import com.examportal.dto.*;
import com.examportal.helper.JsonConverter;
import com.examportal.models.Category;
import com.examportal.models.EOperation;
import com.examportal.repository.CategoryRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.services.AuditLogService;
import com.examportal.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public ResponseDTO<List<CategoryDTO>> getAllCategories() {

        List<Object[]> results = categoryRepository.findCategoriesWithQuizCounts();


        return new ResponseDTO<>(true, null, results.stream().map(result->{
            Integer id = (Integer) result[0];
            String name = (String) result[1];
            String description = (String) result[2];
            boolean isActive = (boolean) result[3];
            Long totalQuizCount = (Long) result[4];
            Long activeQuizCount = (Long) result[5];
            Long inActiveQuizCount = (Long) result[6];
            CategoryQuizCountDTO categoryQuizCountDTO = new CategoryQuizCountDTO(totalQuizCount, activeQuizCount, inActiveQuizCount);
            return new CategoryDTO(id, name, description, isActive, categoryQuizCountDTO);
        }).collect(Collectors.toList()));
    }

    @Override
    public ResponseDTO<Category> saveCategory(CategoryDTO category) {
        if(categoryRepository.existsByName(category.getName())){
            return new ResponseDTO<>(false, "Category name already exists.", null);
        }
        Category categoryEntity = new Category();
        categoryEntity.setName(category.getName());
        categoryEntity.setDescription(category.getDescription());
        categoryEntity.setIsActive(true);

        Category result = categoryRepository.save(categoryEntity);

        auditLogService.saveAuditLog(EOperation.CREATE, result);

        return new ResponseDTO<>(true, "Category saved successfully.", result);
    }

    @Override
    public ResponseDTO<Category> updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(()-> new IllegalArgumentException("Category id does not exists."));
        if(hasChanges(category, categoryDTO)){
//            if(!categoryDTO.getName().equals(category.getName()) && categoryRepository.existsByName(categoryDTO.getName())){
//                return new ResponseDTO<>(false, "Category name already exists", null);
//            }
//            category.setName(categoryDTO.getName().trim());
            category.setDescription(categoryDTO.getDescription().trim());
            Category result = categoryRepository.save(category);
            auditLogService.saveAuditLog(EOperation.UPDATE, new Category(result.getId(), result.getName(), result.getDescription(), null, result.getIsActive()));
            return new ResponseDTO<>(true, "Category updated successfully.", result);
        }else {
            return new ResponseDTO<>(true, "No changes were made as the data is already up to date.", null);
        }
    }

    @Override
    public ResponseDTO<List<Category>> updateCategoriesStatus(UpdateCategoriesStatusDTO updateCategoriesStatusDTO) {
        List<Category> categories = categoryRepository.findAllById(updateCategoriesStatusDTO.getIds());
        List<Category> categoriesAudit = new ArrayList<>();
        for(Category category : categories){
            category.setIsActive(updateCategoriesStatusDTO.getIsActive());
        }
        List<Category> resultList = categoryRepository.saveAll(categories);
        resultList.forEach(category -> {
            categoriesAudit.add(new Category(category.getId(), category.getName(), category.getDescription(), null, category.getIsActive()));
        });
        auditLogService.saveAuditLog(EOperation.UPDATE , categoriesAudit);
        return new ResponseDTO<>(true, "Categories status updated successfully.", resultList);
    }

    @Override
    public EntityUpdateTrailDTO<CategoryDTO> getCategoryUpdateTrailDTO(Integer entityId) {

        List<AuditLogDTO<String>> auditLogDTOList = auditLogService.getAuditLogByEntity("Category", Long.valueOf(entityId));
        EntityUpdateTrailDTO<CategoryDTO> categoryUpdateTrailDTO =  new EntityUpdateTrailDTO<>();
        List<AuditLogDTO<CategoryDTO>> categoryAuditLogList = new ArrayList<>();
        auditLogDTOList.forEach(auditLogDTO ->{
            Category category = jsonConverter.convertToObject(auditLogDTO.getData(), Category.class);
            CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName(), category.getDescription(), category.getIsActive(), null);
            if(auditLogDTO.getActionType().equals(EOperation.CREATE)){
                categoryUpdateTrailDTO.setActualEntity(
                        categoryDTO
                );
                categoryUpdateTrailDTO.setCreatedBy(auditLogDTO.getUpdatedBy());
                categoryUpdateTrailDTO.setCreatedAt(auditLogDTO.getDate());
            }else {
                categoryAuditLogList.add(new AuditLogDTO<>(auditLogDTO.getId(), null, categoryDTO, auditLogDTO.getUpdatedBy(), auditLogDTO.getDate()));
            }
        });

        categoryUpdateTrailDTO.setAuditLogDTOList(categoryAuditLogList);
        return categoryUpdateTrailDTO;
    }

    @Override
    public List<EntitiesStatusUpdateTrailDTO<CategoryDTO>> getCategoriesStatusUpdateTrailDTO() {
        List<AuditLogDTO<String>> auditLogDTOList = auditLogService.getAuditLogByEntity("Category", 0L);
        List<EntitiesStatusUpdateTrailDTO<CategoryDTO>> entitiesStatusUpdateTrailDTOS = new ArrayList<>();
        auditLogDTOList.forEach(auditLog->{
            List<Category> categoryList = jsonConverter.convertToListOfObject(auditLog.getData(), Category.class);
            entitiesStatusUpdateTrailDTOS.add(
                    new EntitiesStatusUpdateTrailDTO<>(
                            auditLog.getUpdatedBy(),
                            auditLog.getDate(),
                            categoryList.stream().map(category -> new CategoryDTO(category.getId(), category.getName(), null, category.getIsActive(), null)).collect(Collectors.toList()),
                            categoryList.get(0).getIsActive()
                    )
            );
        });
        return entitiesStatusUpdateTrailDTOS;
    }

    private boolean hasChanges(Category category, CategoryDTO categoryDTO){
        return !category.getDescription().trim().equals(categoryDTO.getDescription().trim());
    }
}
