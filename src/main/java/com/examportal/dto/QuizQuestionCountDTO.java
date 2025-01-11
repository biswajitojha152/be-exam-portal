package com.examportal.dto;

public class QuizQuestionCountDTO {
    private Long totalQuestionCount;
    private Long activeQuestionCount;
    private Long inActiveQuestionCount;

    public QuizQuestionCountDTO() {
    }

    public QuizQuestionCountDTO(Long totalQuestionCount, Long activeQuestionCount, Long inActiveQuestionCount) {
        this.totalQuestionCount = totalQuestionCount;
        this.activeQuestionCount = activeQuestionCount;
        this.inActiveQuestionCount = inActiveQuestionCount;
    }

    public Long getTotalQuestionCount() {
        return totalQuestionCount;
    }

    public void setTotalQuestionCount(Long totalQuestionCount) {
        this.totalQuestionCount = totalQuestionCount;
    }

    public Long getActiveQuestionCount() {
        return activeQuestionCount;
    }

    public void setActiveQuestionCount(Long activeQuestionCount) {
        this.activeQuestionCount = activeQuestionCount;
    }

    public Long getInActiveQuestionCount() {
        return inActiveQuestionCount;
    }

    public void setInActiveQuestionCount(Long inActiveQuestionCount) {
        this.inActiveQuestionCount = inActiveQuestionCount;
    }
}
