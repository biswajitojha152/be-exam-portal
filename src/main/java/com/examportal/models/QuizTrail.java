package com.examportal.models;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class QuizTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private User user;

    private Integer totalQuestions;

    private Integer attemptedQuestions;

    private Integer correctAnswer;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant attemptedAt;

    private Short timeTaken;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    public QuizTrail() {
    }

    public QuizTrail(Integer id, Quiz quiz, User user, Integer totalQuestions, Integer attemptedQuestions, Integer correctAnswer, Instant attemptedAt, Short timeTaken , EStatus status) {
        this.id = id;
        this.quiz = quiz;
        this.user = user;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }
}
