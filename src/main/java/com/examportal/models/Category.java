package com.examportal.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
//    @JsonBackReference
    private List<Quiz> quiz;

    public Category() {
    }

    public Category(Integer id, String name, List<Quiz> quiz) {
        this.id = id;
        this.name = name;
        this.quiz = quiz;
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

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }
}
