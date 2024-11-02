package com.examportal.repository;

import com.examportal.models.Question;
import com.examportal.models.Quiz;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByQuiz(Quiz quiz, Sort sort);
}
