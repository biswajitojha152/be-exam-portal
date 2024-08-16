package com.examportal.dto;

public class CategoryDTO {
    private Integer id;
    private String name;
    private Long quizCount;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer id, String name, Long quizCount) {
        this.id = id;
        this.name = name;
        this.quizCount = quizCount;
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

    public Long getQuizCount() {
        return quizCount;
    }

    public void setQuizCount(Long quizCount) {
        this.quizCount = quizCount;
    }
}
