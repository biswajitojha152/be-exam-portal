package com.examportal.dto;

import java.util.List;

public class QuestionDTO {
    private Integer id;
    private String name;
    private Integer quizId;
    private List<OptionDTO> optionDTOList;
    private boolean isActive;

    public QuestionDTO() {
    }

    public QuestionDTO(Integer id, String name, Integer quizId, List<OptionDTO> optionDTOList, boolean isActive) {
        this.id = id;
        this.name = name;
        this.quizId = quizId;
        this.optionDTOList = optionDTOList;
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

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public List<OptionDTO> getOptionDTOList() {
        return optionDTOList;
    }

    public void setOptionDTOList(List<OptionDTO> optionDTOList) {
        this.optionDTOList = optionDTOList;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
