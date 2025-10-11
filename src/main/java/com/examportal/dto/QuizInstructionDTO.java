package com.examportal.dto;

import java.util.List;

public class QuizInstructionDTO {
    private String quizName;
    private String category;
    private String quizDescription;
    private Byte totalQuestions;
    private Byte duration;
    private List<String> instructions;

    public QuizInstructionDTO() {
    }

    public QuizInstructionDTO(String quizName, String category, String quizDescription, Byte totalQuestions, Byte duration, List<String> instructions) {
        this.quizName = quizName;
        this.category = category;
        this.quizDescription = quizDescription;
        this.totalQuestions = totalQuestions;
        this.duration = duration;
        this.instructions = instructions;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

    public Byte getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Byte totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Byte getDuration() {
        return duration;
    }

    public void setDuration(Byte duration) {
        this.duration = duration;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }
}
