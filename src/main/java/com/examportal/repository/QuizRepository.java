package com.examportal.repository;


import com.examportal.dto.projection.QuizIdsWithQuizCountProjection;
import com.examportal.dto.projection.QuizProjection;
import com.examportal.dto.projection.QuizProjectionWithQuestionCount;
import com.examportal.models.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    Boolean existsByName(String quizName);

    @Query(
            value = "SELECT " +
                    "q.id AS id, " +
                    "q.name AS name, " +
                    "c.id AS categoryId, " +
                    "c.name AS categoryName, " +
//                    "NULL AS questionDTOList, " +
                    "q.description AS description, " +
                    "COUNT(ques.id) AS totalQuestionCount, " +
                    "COUNT(CASE WHEN ques.isActive=true THEN 1 END) as activeQuestionCount, " +
                    "COUNT(CASE WHEN ques.isActive=false THEN 1 END) as inActiveQuestionCount, " +
                    "q.attemptableCount AS attemptableCount, " +
                    "q.duration AS duration, " +
                    "q.isActive AS isActive " +
                    "FROM Quiz q " +
                    "LEFT JOIN q.category c " +
                    "LEFT JOIN q.questions ques " +
                    "WHERE (:searchInput IS NULL OR q.name LIKE CONCAT('%', :searchInput, '%') OR q.description LIKE CONCAT('%', :searchInput, '%')) " +
                    "AND (:categoryId IS NULL OR c.id = :categoryId) " +
                    "GROUP BY q.id"
    )
    List<QuizProjectionWithQuestionCount> findQuizListWithQuestionCount(@Param("searchInput") String searchInput, @Param("categoryId") Integer categoryId, Sort sort);

    @Query(
            value = "SELECT " +
                    "q.id AS id, " +
                    "q.name AS name, " +
                    "c.id AS categoryId, " +
                    "c.name AS categoryName, " +
//                    "NULL AS questionDTOList, " +
                    "q.description AS description, " +
                    "COUNT(ques.id) AS totalQuestionCount, " +
                    "COUNT(CASE WHEN ques.isActive=true THEN 1 END) as activeQuestionCount, " +
                    "COUNT(CASE WHEN ques.isActive=false THEN 1 END) as inActiveQuestionCount, " +
                    "q.attemptableCount AS attemptableCount, " +
                    "q.duration AS duration, " +
                    "q.isActive AS isActive " +
                    "FROM Quiz q " +
                    "LEFT JOIN q.category c " +
                    "LEFT JOIN q.questions ques " +
                    "WHERE (:searchInput IS NULL OR q.name LIKE CONCAT('%', :searchInput, '%') OR q.description LIKE CONCAT('%', :searchInput, '%')) " +
                    "AND (:categoryId IS NULL OR c.id = :categoryId) " +
                    "GROUP BY q.id"
    )
    Page<QuizProjectionWithQuestionCount> findQuizListWithQuestionCount(@Param("searchInput") String searchInput, @Param("categoryId") Integer categoryId, Pageable pageable);

    @Query(
            value = "SELECT " +
                    "GROUP_CONCAT(CASE WHEN q.is_active = true THEN q.id END) AS activeIds, " +
                    "GROUP_CONCAT(CASE WHEN q.is_active = false THEN q.id END) AS inActiveIds " +
                    "FROM Quiz q " +
                    "WHERE (:searchInput IS NULL OR q.name LIKE CONCAT('%', :searchInput, '%') OR q.description LIKE CONCAT('%', :searchInput, '%')) " +
                    "AND (:categoryId IS NULL OR q.category_id = :categoryId)",
            nativeQuery = true
    )
    QuizIdsWithQuizCountProjection findQuizIdsWithQuizCount(@Param("searchInput") String searchInput, @Param("categoryId") Integer categoryId);

    List<Quiz> findAllByIsRecommendedTrueAndIsActiveTrue();

    Optional<Quiz> findByIdAndIsActiveTrue(Integer id);

    @Query(
            value = "SELECT " +
                    "q.id AS id, " +
                    "q.name AS name, " +
                    "c.name AS categoryName, " +
//                    "NULL AS questionDTOList, " +
                    "q.description AS description, " +
                    "q.attemptableCount AS attemptableCount, " +
                    "q.duration AS duration " +
                    "FROM Quiz q " +
                    "LEFT JOIN q.category c " +
                    "LEFT JOIN q.questions ques " +
                    "WHERE (:searchInput IS NULL OR q.name LIKE CONCAT('%', :searchInput, '%') OR q.description LIKE CONCAT('%', :searchInput, '%')) " +
                    "AND (:categoryId IS NULL OR c.id = :categoryId) "
    )
    List<QuizProjection> findQuizList(@Param("searchInput") String searchInput, @Param("categoryId") Integer categoryId, Sort sort);

    @Query(
            "SELECT q.id AS id, " +
                    "q.name AS name, " +
                    "q.category.name AS categoryName, " +
                    "q.description AS description, " +
                    "q.attemptableCount AS attemptableCount, " +
                    "q.duration AS duration " +
                    "FROM Quiz q " +
                    "WHERE q.isActive=true " +
                    "AND (:searchInput IS NULL OR q.name LIKE CONCAT('%', :searchInput, '%') OR q.description LIKE CONCAT('%', :searchInput, '%')) " +
                    "AND (:categoryId IS NULL OR q.category.id = :categoryId) "
    )
    Page<QuizProjection> findQuizList(@Param("searchInput") String searchInput, @Param("categoryId") Integer categoryId, Pageable pageable);


}
