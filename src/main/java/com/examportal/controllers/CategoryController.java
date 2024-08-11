package com.examportal.controllers;

import com.examportal.dto.CategoryDTO;
import com.examportal.models.Category;
import com.examportal.payload.response.MessageResponse;
import com.examportal.services.CategoryService;
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
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @PostMapping("/saveCategory")
    public ResponseEntity<MessageResponse> saveCategory(@RequestBody CategoryDTO category){
        Category categorySave = categoryService.saveCategory(category);
        if(categorySave == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Category name already exists."));
        }

        return ResponseEntity.created(URI.create(String.format("category/%s", categorySave.getId()))).body(new MessageResponse("Category created"));
    }
}
