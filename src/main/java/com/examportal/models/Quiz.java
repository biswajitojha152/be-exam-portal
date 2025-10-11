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

    private String description;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Byte attemptableCount;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Byte duration;

    private boolean isActive;

    public Quiz() {
    }

    public Quiz(Integer id, String name, Category category, List<Question> questions, List<QuizTrail> quizTrails, String description, Byte attemptableCount, Byte duration, boolean isActive) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.questions = questions;
        this.quizTrails = quizTrails;
        this.description = description;
        this.attemptableCount = attemptableCount;
        this.duration = duration;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getAttemptableCount() {
        return attemptableCount;
    }

    public void setAttemptableCount(Byte attemptableCount) {
        this.attemptableCount = attemptableCount;
    }

    public Byte getDuration() {
        return duration;
    }

    public void setDuration(Byte duration) {
        this.duration = duration;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
