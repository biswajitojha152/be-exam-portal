package com.examportal.dto;

public class QuizSubmitResponse {
    private Integer totalMark;
    private Integer securedMark;

    public QuizSubmitResponse() {
    }

    public QuizSubmitResponse(Integer totalMark, Integer securedMark) {
        this.totalMark = totalMark;
        this.securedMark = securedMark;
    }

    public Integer getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(Integer totalMark) {
        this.totalMark = totalMark;
    }

    public Integer getSecuredMark() {
        return securedMark;
    }

    public void setSecuredMark(Integer securedMark) {
        this.securedMark = securedMark;
    }
}
