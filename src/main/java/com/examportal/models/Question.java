package com.examportal.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private List<Option> optionList;

    @ManyToOne
    private Quiz quiz;

    private boolean isActive;

    public Question() {
    }

    public Question(Integer id, String name, List<Option> optionList, Quiz quiz, boolean isActive) {
        this.id = id;
        this.name = name;
        this.optionList = optionList;
        this.quiz = quiz;
        this.isActive = isActive;
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

    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
