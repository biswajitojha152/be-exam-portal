package com.examportal.dto;

public class CategoryQuizCountDTO {
    private Long totalQuizCount;
    private Long activeQuizCount;
    private Long inActiveQuizCount;

    public CategoryQuizCountDTO() {
    }

    public CategoryQuizCountDTO(Long totalQuizCount, Long activeQuizCount, Long inActiveQuizCount) {
        this.totalQuizCount = totalQuizCount;
        this.activeQuizCount = activeQuizCount;
        this.inActiveQuizCount = inActiveQuizCount;
    }

    public Long getTotalQuizCount() {
        return totalQuizCount;
    }

    public void setTotalQuizCount(Long totalQuizCount) {
        this.totalQuizCount = totalQuizCount;
    }

    public Long getActiveQuizCount() {
        return activeQuizCount;
    }

    public void setActiveQuizCount(Long activeQuizCount) {
        this.activeQuizCount = activeQuizCount;
    }

    public Long getInActiveQuizCount() {
        return inActiveQuizCount;
    }

    public void setInActiveQuizCount(Long inActiveQuizCount) {
        this.inActiveQuizCount = inActiveQuizCount;
    }
}
