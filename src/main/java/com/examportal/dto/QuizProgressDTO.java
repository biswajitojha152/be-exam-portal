package com.examportal.dto;

import java.util.List;

public class QuizProgressDTO {
    private Integer id;
    private String name;
    private List<QuizProgressQuestionDTO> quizProgressQuestionDTOList;

    public QuizProgressDTO() {
    }

    public QuizProgressDTO(Integer id, String name, List<QuizProgressQuestionDTO> quizProgressQuestionDTOList) {
        this.id = id;
        this.name = name;
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

    public List<QuizProgressQuestionDTO> getQuizProgressQuestionDTOList() {
        return quizProgressQuestionDTOList;
    }

    public void setQuizProgressQuestionDTOList(List<QuizProgressQuestionDTO> quizProgressQuestionDTOList) {
        this.quizProgressQuestionDTOList = quizProgressQuestionDTOList;
    }
}
