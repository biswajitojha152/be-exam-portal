package com.examportal.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private boolean isActive;
    private CategoryQuizCountDTO categoryQuizCountDTO;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer id, String name, String description, boolean isActive, CategoryQuizCountDTO categoryQuizCountDTO) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.categoryQuizCountDTO = categoryQuizCountDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public CategoryQuizCountDTO getCategoryQuizCountDTO() {
        return categoryQuizCountDTO;
    }

    public void setCategoryQuizCountDTO(CategoryQuizCountDTO categoryQuizCountDTO) {
        this.categoryQuizCountDTO = categoryQuizCountDTO;
    }
}
