package com.examportal.repository;

import com.examportal.models.QuizTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTrailRepository extends JpaRepository<QuizTrail, Integer> {
}
