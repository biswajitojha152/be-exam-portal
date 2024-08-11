package com.examportal.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JsonManagedReference
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz")
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz")
    private List<QuizTrail> quizTrails;

    public Quiz() {
    }

    public Quiz(Integer id, String name, Category category, List<Question> questions, List<QuizTrail> quizTrails) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.questions = questions;
        this.quizTrails = quizTrails;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<QuizTrail> getQuizTrails() {
        return quizTrails;
    }

    public void setQuizTrails(List<QuizTrail> quizTrails) {
        this.quizTrails = quizTrails;
    }
}
