package com.examportal.dto;

public class QuestionChangeDTO {
    private Integer questionId;
    private Long optionId;

    public QuestionChangeDTO() {
    }

    public QuestionChangeDTO(Integer questionId, Long optionId) {
        this.questionId = questionId;
        this.optionId = optionId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }
}
