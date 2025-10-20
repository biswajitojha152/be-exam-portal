package com.examportal.services.impl;

import com.examportal.dto.*;
import com.examportal.models.*;
import com.examportal.repository.CategoryRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.repository.QuizTrailRepository;
import com.examportal.repository.UserRepository;
import com.examportal.services.DashboardService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizTrailRepository quizTrailRepository;

    @Override
    public DashboardData getDashboardSummary() {
        return new DashboardData(userRepository.count(), quizRepository.count(), categoryRepository.count(), new AttemptsDTO(quizTrailRepository.count(), quizTrailRepository.countByStatus(EStatus.PASSED), quizTrailRepository.countByStatus(EStatus.FAILED)));
    }

    @Override
    public PaginatedResponse<QuizTrailDTO> getQuizTrail(QuizTrailArgumentDTO quizTrailArgumentDTO) {
        Specification<QuizTrail> quizTrailSpecification = (root, query, criteriaBuilder)->{
            Predicate predicate = criteriaBuilder.conjunction();
            if(quizTrailArgumentDTO.getFromDate() != null){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("attemptedAt"), quizTrailArgumentDTO.getFromDate()));
            }
            if(quizTrailArgumentDTO.getToDate() != null){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("attemptedAt"), quizTrailArgumentDTO.getToDate()));
            }
            if(quizTrailArgumentDTO.getCategoryId() != null){
                Join<QuizTrail, Quiz> quizJoin = root.join("quiz", JoinType.INNER);
                Join<Quiz, Category> categoryJoin = quizJoin.join("category", JoinType.INNER);
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(categoryJoin.get("id"), quizTrailArgumentDTO.getCategoryId()));
            }
            if(quizTrailArgumentDTO.getQuizId() != null){
                Join<QuizTrail, Quiz> quizJoin = root.join("quiz", JoinType.INNER);
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(quizJoin.get("id"), quizTrailArgumentDTO.getQuizId()));
            }
            if(quizTrailArgumentDTO.getSearchByUsername() != null){
                Join<QuizTrail, User> userJoin = root.join("user", JoinType.INNER);
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(userJoin.get("username"), "%"+quizTrailArgumentDTO.getSearchByUsername()+"%"));
            }
            if(quizTrailArgumentDTO.getStatus() != null){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), quizTrailArgumentDTO.getStatus()));
            }
            return predicate;
        };

        PaginatedResponse<QuizTrailDTO> paginatedResponse = new PaginatedResponse<>();

        if(quizTrailArgumentDTO.getPageNo() == 0 && quizTrailArgumentDTO.getPageSize() == 0){
            List<QuizTrail> quizTrails = quizTrailRepository.findAll(quizTrailSpecification, Sort.by(Sort.Direction.DESC, "id"));
            int totalElements= quizTrails.size();
            paginatedResponse.setData(quizTrails.stream().map(quizTrail -> new QuizTrailDTO(
                    quizTrail.getId(),
                    new QuizDTO(quizTrail.getQuiz().getId(), quizTrail.getQuiz().getName(), quizTrail.getQuiz().getCategory().getId(), quizTrail.getQuiz().getCategory().getName(), null, null, null, null, null,quizTrail.getQuiz().getIsActive()),
                    quizTrail.getUser().getUsername(),
                    quizTrail.getTotalQuestions(),
                    quizTrail.getAttemptedQuestions(),
                    quizTrail.getCorrectAnswer(),
                    quizTrail.getAttemptedAt(),
                    quizTrail.getTimeTaken(),
                    quizTrail.getStatus().getRoleValue()
            )).collect(Collectors.toList()));
            paginatedResponse.setIsLastPage(true);
            paginatedResponse.setPageNumber(0);
            paginatedResponse.setPageSize(totalElements);
            paginatedResponse.setTotalElements(totalElements);
            paginatedResponse.setTotalPages(1);
        }else {
            Pageable pageable = PageRequest.of(quizTrailArgumentDTO.getPageNo(), quizTrailArgumentDTO.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
            Page<QuizTrail> page = quizTrailRepository.findAll(quizTrailSpecification, pageable);
            paginatedResponse.setData(page.getContent().stream().map(quizTrail -> new QuizTrailDTO(
                    quizTrail.getId(),
                    new QuizDTO(quizTrail.getQuiz().getId(), quizTrail.getQuiz().getName(), quizTrail.getQuiz().getCategory().getId(), quizTrail.getQuiz().getCategory().getName(), null, null, null, null, null, quizTrail.getQuiz().getIsActive()),
                    quizTrail.getUser().getUsername(),
                    quizTrail.getTotalQuestions(),
                    quizTrail.getAttemptedQuestions(),
                    quizTrail.getCorrectAnswer(),
                    quizTrail.getAttemptedAt(),
                    quizTrail.getTimeTaken(),
                    quizTrail.getStatus().getRoleValue()
            )).collect(Collectors.toList()));
            paginatedResponse.setIsLastPage(page.isLast());
            paginatedResponse.setPageNumber(page.getNumber());
            paginatedResponse.setPageSize(page.getSize());
            paginatedResponse.setTotalElements(page.getTotalElements());
            paginatedResponse.setTotalPages(page.getTotalPages());
        }
        return paginatedResponse;
    }
}
