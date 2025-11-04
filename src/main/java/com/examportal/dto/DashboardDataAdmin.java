package com.examportal.dto;

public class DashboardDataAdmin {
    private Long numberOfUser;
    private Long numberOfQuiz;
    private Long numberOfCategory;
    private AttemptsDTO attemptsDTO;
    public DashboardDataAdmin() {
    }

    public DashboardDataAdmin(Long numberOfUser, Long numberOfQuiz, Long numberOfCategory, AttemptsDTO attemptsDTO) {
        this.numberOfUser = numberOfUser;
        this.numberOfQuiz = numberOfQuiz;
        this.numberOfCategory = numberOfCategory;
        this.attemptsDTO = attemptsDTO;
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

    public AttemptsDTO getAttemptsDTO() {
        return attemptsDTO;
    }

    public void setAttemptsDTO(AttemptsDTO attemptsDTO) {
        this.attemptsDTO = attemptsDTO;
    }
}
