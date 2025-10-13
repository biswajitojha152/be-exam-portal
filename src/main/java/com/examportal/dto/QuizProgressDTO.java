package com.examportal.dto;

import java.time.Instant;
import java.util.List;

public class QuizProgressDTO {
    private Integer id;
    private String name;
    private Instant quizStartTime;
    private Instant lastFetchedTime;
    private Long quizDurationInSeconds;
    private List<QuizProgressQuestionDTO> quizProgressQuestionDTOList;

    public QuizProgressDTO() {
    }

    public QuizProgressDTO(Integer id, String name, Instant quizStartTime, Instant lastFetchedTime, Long quizDurationInSeconds,List<QuizProgressQuestionDTO> quizProgressQuestionDTOList) {
        this.id = id;
        this.name = name;
        this.quizStartTime = quizStartTime;
        this.lastFetchedTime = lastFetchedTime;
        this.quizDurationInSeconds = quizDurationInSeconds;
        this.quizProgressQuestionDTOList = quizProgressQuestionDTOList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getQuizStartTime() {
        return quizStartTime;
    }

    public void setQuizStartTime(Instant quizStartTime) {
        this.quizStartTime = quizStartTime;
    }

    public Instant getLastFetchedTime() {
        return lastFetchedTime;
    }

    public void setLastFetchedTime(Instant lastFetchedTime) {
        this.lastFetchedTime = lastFetchedTime;
    }

    public Long getQuizDurationInSeconds() {
        return quizDurationInSeconds;
    }

    public void setQuizDurationInSeconds(Long quizDurationInSeconds) {
        this.quizDurationInSeconds = quizDurationInSeconds;
    }

    public List<QuizProgressQuestionDTO> getQuizProgressQuestionDTOList() {
        return quizProgressQuestionDTOList;
    }

    public void setQuizProgressQuestionDTOList(List<QuizProgressQuestionDTO> quizProgressQuestionDTOList) {
        this.quizProgressQuestionDTOList = quizProgressQuestionDTOList;
    }
}
