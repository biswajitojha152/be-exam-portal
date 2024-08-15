package com.examportal.dto;

public class DashboardData {
    private Long numberOfUser;
    private Long numberOfQuiz;
    private Long numberOfCategory;

    public DashboardData() {
    }

    public DashboardData(Long numberOfUser, Long numberOfQuiz, Long numberOfCategory) {
        this.numberOfUser = numberOfUser;
        this.numberOfQuiz = numberOfQuiz;
        this.numberOfCategory = numberOfCategory;
    }

    public Long getNumberOfUser() {
        return numberOfUser;
    }

    public void setNumberOfUser(Long numberOfUser) {
        this.numberOfUser = numberOfUser;
    }

    public Long getNumberOfQuiz() {
        return numberOfQuiz;
    }

    public void setNumberOfQuiz(Long numberOfQuiz) {
        this.numberOfQuiz = numberOfQuiz;
    }

    public Long getNumberOfCategory() {
        return numberOfCategory;
    }

    public void setNumberOfCategory(Long numberOfCategory) {
        this.numberOfCategory = numberOfCategory;
    }
}
