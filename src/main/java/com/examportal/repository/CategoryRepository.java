package com.examportal.repository;

import com.examportal.models.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

    Boolean existsByName(String categoryName);

    @Query(value = "SELECT c.id, c.name, c.description, c.is_active, " +
            "COUNT(q.id) AS total_quiz_count, " +
            "COUNT(CASE WHEN q.is_active=TRUE THEN 1 END) AS active_quiz_count, " +
            "COUNT(CASE WHEN q.is_active=false THEN 1 END) AS inactive_quiz " +
            "FROM categories c " +
            "LEFT JOIN quiz q ON c.id=q.id " +
            "GROUP BY c.id " +
            "ORDER BY c.id DESC", nativeQuery = true)
    List<Object[]> findCategoriesWithQuizCounts();
}
