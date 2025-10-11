package com.examportal.dto;

import java.util.List;

public class QuizIdsWithQuizCountDTO {
    private List<Integer> activeIds;
    private List<Integer> inActiveIds;

    public QuizIdsWithQuizCountDTO() {
    }

    public QuizIdsWithQuizCountDTO(List<Integer> activeIds, List<Integer> inActiveIds) {
        this.activeIds = activeIds;
        this.inActiveIds = inActiveIds;
    }

    public List<Integer> getActiveIds() {
        return activeIds;
    }

    public void setActiveIds(List<Integer> activeIds) {
        this.activeIds = activeIds;
    }

    public List<Integer> getInActiveIds() {
        return inActiveIds;
    }

    public void setInActiveIds(List<Integer> inActiveIds) {
        this.inActiveIds = inActiveIds;
    }
}
