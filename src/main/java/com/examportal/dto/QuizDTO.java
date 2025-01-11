package com.examportal.dto;

import java.util.List;

public class QuizDTO {
    private Integer id;
    private String name;
    private Integer categoryId;
    private String categoryName;
    private List<QuestionDTO> questionDTOList;
    private String description;
    private QuizQuestionCountDTO quizQuestionCountDTO;
    private boolean isActive;

    public QuizDTO() {
    }

    public QuizDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public QuizDTO(Integer id, String name, Integer categoryId, String categoryName, List<QuestionDTO> questionDTOList, String description, QuizQuestionCountDTO quizQuestionCountDTO, boolean isActive) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.questionDTOList = questionDTOList;
        this.description = description;
        this.quizQuestionCountDTO = quizQuestionCountDTO;
        this.isActive = isActive;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<QuestionDTO> getQuestionDTOList() {
        return questionDTOList;
    }

    public void setQuestionDTOList(List<QuestionDTO> questionDTOList) {
        this.questionDTOList = questionDTOList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuizQuestionCountDTO getQuizQuestionCountDTO() {
        return quizQuestionCountDTO;
    }

    public void setQuizQuestionCountDTO(QuizQuestionCountDTO quizQuestionCountDTO) {
        this.quizQuestionCountDTO = quizQuestionCountDTO;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
