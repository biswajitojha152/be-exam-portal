package com.examportal.repository;

import com.examportal.dto.QuizDTO;
import com.examportal.models.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    Boolean existsByName(String quizName);

    @Query(
            "SELECT " +
                    "q.id AS id, " +
                    "q.name AS name, " +
                    "NULL AS categoryId, " +
                    "c.name AS categoryName, " +
                    "NULL AS questionDTOList, " +
                    "q.description AS description, " +
                    "COUNT(ques.id) AS questionCount, " +
                    "q.isActive AS isActive " +
                    "FROM Quiz q " +
                    "LEFT JOIN q.category c " +
                    "LEFT JOIN q.questions ques " +
                    "GROUP BY q.id"
    )
    List<Object[]> findQuizListWithQuestionCount(Specification<Quiz> specification);

    @Query(
            "SELECT " +
                    "q.id AS id, " +
                    "q.name AS name, " +
                    "NULL AS categoryId, " +
                    "c.name AS categoryName, " +
                    "NULL AS questionDTOList, " +
                    "q.description AS description, " +
                    "COUNT(ques.id) AS questionCount, " +
                    "q.isActive AS isActive " +
                    "FROM Quiz q " +
                    "LEFT JOIN q.category c " +
                    "LEFT JOIN q.questions ques " +
                    "GROUP BY q.id"
    )
    Page<Object[]> findQuizListWithQuestionCount(Specification<Quiz> specification, Pageable pageable);
}
