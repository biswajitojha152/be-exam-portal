package com.examportal.dto;

public class AttemptsDTO {
    private Long totalAttempts;
    private Long passedAttempts;
    private Long failedAttempts;

    public AttemptsDTO() {
    }

    public AttemptsDTO(Long totalAttempts, Long passedAttempts, Long failedAttempts) {
        this.totalAttempts = totalAttempts;
        this.passedAttempts = passedAttempts;
        this.failedAttempts = failedAttempts;
    }

    public Long getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(Long totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public Long getPassedAttempts() {
        return passedAttempts;
    }

    public void setPassedAttempts(Long passedAttempts) {
        this.passedAttempts = passedAttempts;
    }

    public Long getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Long failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
}
