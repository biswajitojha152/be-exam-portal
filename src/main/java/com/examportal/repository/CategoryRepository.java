package com.examportal.repository;

import com.examportal.dto.projection.CategoryProjection;
import com.examportal.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

    Boolean existsByName(String categoryName);

    @Query(value = "SELECT c.id AS id, " +
            "c.name AS name, " +
            "c.description AS description, " +
            "c.is_active AS isActive, " +
            "COUNT(q.id) AS totalQuizCount, " +
            "COUNT(CASE WHEN q.is_active=TRUE THEN 1 END) AS activeQuizCount, " +
            "COUNT(CASE WHEN q.is_active=false THEN 1 END) AS inActiveQuizCount " +
            "FROM categories c " +
            "LEFT JOIN quiz q ON c.id=q.category_id " +
            "GROUP BY c.id " +
            "ORDER BY c.id DESC", nativeQuery = true)
    List<CategoryProjection> findCategoriesWithQuizCounts();

    List<Category> findAllByIsActiveTrue();
}
