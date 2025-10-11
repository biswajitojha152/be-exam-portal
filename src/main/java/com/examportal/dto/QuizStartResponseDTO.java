package com.examportal.dto;

public class QuizStartResponseDTO {
    private Integer quizId;
    private Integer firstQuestionId;

    public QuizStartResponseDTO() {
    }

    public QuizStartResponseDTO(Integer quizId, Integer firstQuestionId) {
        this.quizId = quizId;
        this.firstQuestionId = firstQuestionId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getFirstQuestionId() {
        return firstQuestionId;
    }

    public void setFirstQuestionId(Integer firstQuestionId) {
        this.firstQuestionId = firstQuestionId;
    }
}
