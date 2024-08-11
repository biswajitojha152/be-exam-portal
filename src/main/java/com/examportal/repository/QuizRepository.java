package com.examportal.repository;

import com.examportal.dto.QuizDTO;
import com.examportal.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Query(value = "select new com.examportal.dto.QuizDTO(q.id, q.name) from Quiz q", nativeQuery = false)
    List<QuizDTO> getAllQuiz();


    Boolean existsByName(String quizName);

}
