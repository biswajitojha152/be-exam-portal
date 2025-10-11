package com.examportal.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class QuizDTO {
    private Integer id;
    @NotBlank
    private String name;
    @NotNull
    private Integer categoryId;
    private String categoryName;
    private List<QuestionDTO> questionDTOList;
    @NotBlank
    private String description;
    private QuizQuestionCountDTO quizQuestionCountDTO;
    @Min(10)
    @Max(120)
    @NotNull
    private Byte attemptableCount;
    @Min(10)
    @Max(120)
    @NotNull
    private Byte duration;
    private boolean isActive;

    public QuizDTO() {
    }


    public QuizDTO(Integer id, String name, Integer categoryId, String categoryName, List<QuestionDTO> questionDTOList, String description, QuizQuestionCountDTO quizQuestionCountDTO, Byte attemptableCount, Byte duration, boolean isActive) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.questionDTOList = questionDTOList;
        this.description = description;
        this.quizQuestionCountDTO = quizQuestionCountDTO;
        this.attemptableCount = attemptableCount;
        this.duration = duration;
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

    public @Min(10) @Max(255) @NotNull Byte getAttemptableCount() {
        return attemptableCount;
    }

    public void setAttemptableCount(@Min(10) @Max(255) @NotNull Byte attemptableCount) {
        this.attemptableCount = attemptableCount;
    }

    public @Min(1) @Max(255) @NotNull Byte getDuration() {
        return duration;
    }

    public void setDuration(@Min(1) @Max(255) @NotNull Byte duration) {
        this.duration = duration;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
