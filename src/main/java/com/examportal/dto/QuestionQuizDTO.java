package com.examportal.dto;

public class QuestionQuizDTO {
    private Integer quizId;
    private Integer questionId;

    public QuestionQuizDTO() {
    }

    public QuestionQuizDTO(Integer quizId, Integer questionId) {
        this.quizId = quizId;
        this.questionId = questionId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}
