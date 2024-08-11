package com.examportal.dto;

import java.util.List;

public class QuizDTO {
    private Integer id;
    private String name;
    private Integer categoryId;
    private List<QuestionDTO> questionDTOList;

    public QuizDTO() {
    }

    public QuizDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public QuizDTO(Integer id, String name, Integer categoryId, List<QuestionDTO> questionDTOList) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.questionDTOList = questionDTOList;
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

    public List<QuestionDTO> getQuestionDTOList() {
        return questionDTOList;
    }

    public void setQuestionDTOList(List<QuestionDTO> questionDTOList) {
        this.questionDTOList = questionDTOList;
    }
}
