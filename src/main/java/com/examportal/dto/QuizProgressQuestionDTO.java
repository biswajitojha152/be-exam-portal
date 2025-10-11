package com.examportal.dto;

import java.util.List;

public class QuizProgressQuestionDTO {
    private Integer id;
    private String name;
    private List<OptionDTO> optionDTOList;
    private boolean isVisited;
    private boolean isMarkedForReview;

    public QuizProgressQuestionDTO() {
    }

    public QuizProgressQuestionDTO(Integer id, String name, List<OptionDTO> optionDTOList, boolean isVisited, boolean isMarkedForReview) {
        this.id = id;
        this.name = name;
        this.optionDTOList = optionDTOList;
        this.isVisited = isVisited;
        this.isMarkedForReview = isMarkedForReview;
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

    public List<OptionDTO> getOptionDTOList() {
        return optionDTOList;
    }

    public void setOptionDTOList(List<OptionDTO> optionDTOList) {
        this.optionDTOList = optionDTOList;
    }

    public boolean getIsVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public boolean getIsMarkedForReview() {
        return isMarkedForReview;
    }

    public void setIsMarkedForReview(boolean isMarkedForReview) {
        this.isMarkedForReview = isMarkedForReview;
    }
}
