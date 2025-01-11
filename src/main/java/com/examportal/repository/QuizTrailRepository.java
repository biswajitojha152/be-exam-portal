package com.examportal.repository;

import com.examportal.models.EStatus;
import com.examportal.models.QuizTrail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizTrailRepository extends JpaRepository<QuizTrail, Integer> {
    List<QuizTrail> findAll(Specification<QuizTrail> quizTrailSpecification, Sort sort);
    Page<QuizTrail> findAll(Specification<QuizTrail> quizTrailSpecification, Pageable pageable);
    Long countByStatus(EStatus status);
}
