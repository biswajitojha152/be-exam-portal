package com.examportal.controllers;

import com.examportal.dto.CategoryDTO;
import com.examportal.dto.EntityUpdateTrailDTO;
import com.examportal.dto.ResponseDTO;
import com.examportal.dto.UpdateCategoriesStatusDTO;
import com.examportal.models.Category;
import com.examportal.payload.response.MessageResponse;
import com.examportal.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories().getData());
    }

    @PostMapping("/saveCategory")
    public ResponseEntity<MessageResponse> saveCategory(@RequestBody @Valid CategoryDTO category){
        ResponseDTO<Category> categorySaveResponse = categoryService.saveCategory(category);
        if(categorySaveResponse.isSuccess()){
            return ResponseEntity.created(URI.create(String.format("category/%s", categorySaveResponse.getData().getId()))).body(new MessageResponse(categorySaveResponse.getMessage()));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(categorySaveResponse.getMessage()));
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<MessageResponse> updateCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        ResponseDTO<Category>  categoryUpdateResponse = categoryService.updateCategory(categoryDTO);
        if(categoryUpdateResponse.isSuccess()){
            return ResponseEntity.ok(new MessageResponse(categoryUpdateResponse.getMessage()));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(categoryUpdateResponse.getMessage()));
    }

    @PutMapping("/updateCategoryStatus")
    public ResponseEntity<MessageResponse> updateCategoryStatus(@RequestBody UpdateCategoriesStatusDTO updateCategoriesStatusDTO){
        ResponseDTO<List<Category>> categoriesUpdateStatusResponse = categoryService.updateCategoriesStatus(updateCategoriesStatusDTO);
        if(categoriesUpdateStatusResponse.isSuccess()){
            return ResponseEntity.ok(new MessageResponse(categoriesUpdateStatusResponse.getMessage()));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(categoriesUpdateStatusResponse.getMessage()));
    }

    @GetMapping("/getCategoryUpdateAuditLog")
    public ResponseEntity<EntityUpdateTrailDTO<CategoryDTO>> getCategoryAuditLog(@RequestParam Integer categoryId){
        System.out.println(categoryId + "CategoryID");
        return ResponseEntity.ok(categoryService.getCategoryUpdateTrailDTO(categoryId));
    }

    @GetMapping("/getCategoryStatusUpdateAuditLog")
    public ResponseEntity<?> getCategoriesStatusAuditLog(){
        return ResponseEntity.ok(categoryService.getCategoriesStatusUpdateTrailDTO());
    }
}
