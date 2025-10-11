package com.examportal.services.impl;

import com.examportal.dto.*;
import com.examportal.dto.projection.CategoryProjection;
import com.examportal.helper.JsonConverter;
import com.examportal.helper.SecurityContextHelper;
import com.examportal.models.Category;
import com.examportal.models.EOperation;
import com.examportal.models.ERole;
import com.examportal.repository.CategoryRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.services.AuditLogService;
import com.examportal.services.CategoryService;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private SecurityContextHelper securityContextHelper;

    @Override
    @Cacheable(value = "categories", key = "'getAllCategories'+'_'+@securityContextHelper.getCurrentUserRole()")
    public ResponseDTO<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_"+ ERole.ADMIN.name()));

        if(isAdmin){
            List<CategoryProjection> results = categoryRepository.findCategoriesWithQuizCounts();
            results.forEach(result->{
                Integer id = result.getId();
                String name = result.getName();
                String description = result.getDescription();
                boolean isActive = result.getIsActive();
                Long totalQuizCount = result.getTotalQuizCount();
                Long activeQuizCount = result.getActiveQuizCount();
                Long inActiveQuizCount = result.getInActiveQuizCount();
                QuizCountDTO quizCountDTO = new QuizCountDTO(totalQuizCount, activeQuizCount, inActiveQuizCount);

                categoryDTOList.add(new CategoryDTO(id, name, description, isActive, quizCountDTO));
            });
        }else {
            List<Category> categoryList = categoryRepository.findAllByIsActiveTrue();
            categoryList.forEach(category -> {
                categoryDTOList.add(new CategoryDTO(category.getId(), category.getName(), null,false, null));
            });
        }

        return new ResponseDTO<>(true, null, categoryDTOList);
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
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
    @CacheEvict(value = "categories", allEntries = true)
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
    @CacheEvict(value = "categories", allEntries = true)
    public ResponseDTO<List<Category>> updateCategoriesStatus(UpdateEntitiesStatusDTO<Integer> updateEntitiesStatusDTO) {
        List<Category> categories = categoryRepository.findAllById(updateEntitiesStatusDTO.getIds());
        EntitiesStatusUpdateAuditDTO<Category> entitiesStatusUpdateAuditDTO = new EntitiesStatusUpdateAuditDTO<>();
        for(Category category : categories){
            category.setIsActive(updateEntitiesStatusDTO.getIsActive());
        }
        List<Category> resultList = categoryRepository.saveAll(categories);
        entitiesStatusUpdateAuditDTO.setRemark(updateEntitiesStatusDTO.getRemark());
        List<Category> categoriesAudit = new ArrayList<>();
        resultList.forEach(category -> {
            categoriesAudit.add(new Category(category.getId(), category.getName(), category.getDescription(), null, category.getIsActive()));
        });
        entitiesStatusUpdateAuditDTO.setDataList(categoriesAudit);
        auditLogService.saveAuditLog(EOperation.UPDATE , entitiesStatusUpdateAuditDTO);
        return new ResponseDTO<>(true, "Categories status updated successfully.", resultList);
    }

    @Override
    public EntityUpdateTrailDTO<CategoryDTO> getCategoryUpdateTrailDTO(Integer entityId) {

        List<AuditLogDTO<String>> auditLogDTOList = auditLogService.getAuditLogByEntity("Category", Long.valueOf(entityId));
        EntityUpdateTrailDTO<CategoryDTO> categoryUpdateTrailDTO =  new EntityUpdateTrailDTO<>();
        List<AuditLogDTO<CategoryDTO>> categoryAuditLogList = new ArrayList<>();
        auditLogDTOList.forEach(auditLogDTO ->{
            Category category = jsonConverter.convertToObject(auditLogDTO.getData(), new TypeReference<Category>() {
            });
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
            EntitiesStatusUpdateAuditDTO<Category> categoryEntitiesStatusUpdateAuditDTO = jsonConverter.convertToObject(auditLog.getData(), new TypeReference<EntitiesStatusUpdateAuditDTO<Category>>() {
            });

            entitiesStatusUpdateTrailDTOS.add(
                    new EntitiesStatusUpdateTrailDTO<>(
                            auditLog.getUpdatedBy(),
                            auditLog.getDate(),
                            categoryEntitiesStatusUpdateAuditDTO.getDataList().stream().map(category -> new CategoryDTO(category.getId(), category.getName(), null, category.getIsActive(), null)).collect(Collectors.toList()),
                            categoryEntitiesStatusUpdateAuditDTO.getRemark(),
                            categoryEntitiesStatusUpdateAuditDTO.getDataList().get(0).getIsActive()
                    )
            );
        });
        return entitiesStatusUpdateTrailDTOS;
    }

    private boolean hasChanges(Category category, CategoryDTO categoryDTO){
        return !category.getDescription().trim().equals(categoryDTO.getDescription().trim());
    }
}
