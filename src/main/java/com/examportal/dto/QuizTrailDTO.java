package com.examportal.dto;

import java.time.Instant;

public class QuizTrailDTO {
    private Integer id;

    private QuizDTO quizDTO;

    private String username;

    private Integer totalQuestions;

    private Integer attemptedQuestions;

    private Integer correctAnswer;

    private Instant attemptedAt;

    private Short timeTaken;

    private String status;

    public QuizTrailDTO() {
    }

    public QuizTrailDTO(Integer id, QuizDTO quizDTO, String username, Integer totalQuestions, Integer attemptedQuestions, Integer correctAnswer, Instant attemptedAt, Short timeTaken, String status) {
        this.id = id;
        this.quizDTO = quizDTO;
        this.username = username;
        this.totalQuestions = totalQuestions;
        this.attemptedQuestions = attemptedQuestions;
        this.correctAnswer = correctAnswer;
        this.attemptedAt = attemptedAt;
        this.timeTaken = timeTaken;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public QuizDTO getQuizDTO() {
        return quizDTO;
    }

    public void setQuizDTO(QuizDTO quizDTO) {
        this.quizDTO = quizDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(Integer attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Instant getAttemptedAt() {
        return attemptedAt;
    }

    public void setAttemptedAt(Instant attemptedAt) {
        this.attemptedAt = attemptedAt;
    }

    public Short getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Short timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
