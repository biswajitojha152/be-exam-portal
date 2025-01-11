package com.examportal.services.impl;

import com.examportal.dto.PaginatedResponse;
import com.examportal.dto.QuestionDTO;
import com.examportal.dto.QuizDTO;
import com.examportal.dto.QuizSubmitResponse;
import com.examportal.models.*;
import com.examportal.repository.CategoryRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.repository.QuizTrailRepository;
import com.examportal.repository.UserRepository;
import com.examportal.services.QuizService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuizTrailRepository quizTrailRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PaginatedResponse<QuizDTO> getAllQuiz(Integer pageNo, Integer pageSize, Integer categoryId, String searchInput) {

        if(categoryId == null  || categoryRepository.existsById(categoryId)){
            Specification<Quiz> specification = (root, query, criteriaBuilder) ->{
                Predicate predicate = criteriaBuilder.conjunction();
                if(searchInput !=null){
                    Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%"+searchInput+"%");
                    Predicate descriptonPredicate = criteriaBuilder.like(root.get("description"), "%"+searchInput+"%");
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(namePredicate, descriptonPredicate));
                }
                if(categoryId != null){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category").get("id"), categoryId));
                }
                return predicate;
            };
            PaginatedResponse<QuizDTO> paginatedResponse = new PaginatedResponse<>();
            if(pageNo==0 && pageSize==0){
                List<Object[]> quizList = quizRepository.findQuizListWithQuestionCount(specification);
                paginatedResponse.setLastPage(true);
                paginatedResponse.setData(quizList.stream().map(quiz -> new QuizDTO((Integer) quiz[0], (String) quiz[1], null,(String) quiz[3], null, (String) quiz[5], null,(boolean) quiz[7])).collect(Collectors.toList()));
                paginatedResponse.setPageNumber(0);
                paginatedResponse.setPageSize(quizList.size());
                paginatedResponse.setTotalElements(quizList.size());
                paginatedResponse.setTotalPages(1);
            }else {
                Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
                Page<Object[]> page = quizRepository.findQuizListWithQuestionCount(specification, pageable);
                paginatedResponse.setData(page.getContent().stream().map(quiz->new QuizDTO((Integer) quiz[0], (String) quiz[1], null, (String)quiz[3], null, (String) quiz[5], null, (boolean) quiz[7])).collect(Collectors.toList()));
                paginatedResponse.setLastPage(page.isLast());
                paginatedResponse.setPageNumber(page.getNumber());
                paginatedResponse.setPageSize(page.getSize());
                paginatedResponse.setTotalElements(page.getTotalElements());
                paginatedResponse.setTotalPages(page.getTotalPages());
            }
            return paginatedResponse;
        }

        throw new IllegalArgumentException("Category not found with id: "+ categoryId);
    }

    @Override
    public Quiz saveQuiz(QuizDTO quizDTO) {
        if(quizRepository.existsByName(quizDTO.getName())){
            return null;
        }

        Optional<Category> category = categoryRepository.findById(quizDTO.getCategoryId());
        if(category.isPresent()){
            return quizRepository.save(new Quiz(null, quizDTO.getName(), category.get(), null, null,  quizDTO.getDescription(),true));
        }
        throw new IllegalArgumentException("Category does not exists.");
    }

    @Override
    public QuizSubmitResponse submitQuiz(QuizDTO quizDTO) {
        Optional<Quiz> quiz = quizRepository.findById(quizDTO.getId());
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(quiz.isPresent() && user.isPresent()){
            List<Question> questions = quiz.get().getQuestions();

            int quizResult = 0;
            int attemptedQuestions=0;

            int quizDTOSize= quizDTO.getQuestionDTOList().size();

            for(int len=0; len < quizDTOSize; len++){
                if(
                        Objects.equals(quizDTO.getQuestionDTOList().get(len).getId(), questions.get(len).getId())
                                &&
                                (quizDTO.getQuestionDTOList().get(len).getAnswer()==null || Arrays.asList(questions.get(len).getOption1(), questions.get(len).getOption2(), questions.get(len).getOption3(), questions.get(len).getOption4()).contains(quizDTO.getQuestionDTOList().get(len).getAnswer()))
                ){
                    if(quizDTO.getQuestionDTOList().get(len).getAnswer()!=null && quizDTO.getQuestionDTOList().get(len).getAnswer().equals(questions.get(len).getAnswer())){
                        quizResult++;
                        attemptedQuestions++;
                    }else if(quizDTO.getQuestionDTOList().get(len).getAnswer()!=null) {
                        attemptedQuestions++;
                    }
                }else{
                    throw new IllegalArgumentException("Question id does not match.");
                }
            }

            quizTrailRepository.save(new QuizTrail(null, quiz.get(), user.get(), questions.size(), attemptedQuestions, quizResult, new Date(), isPassed(questions.size(), quizResult) ? EStatus.PASSED : EStatus.FAILED));

            return new QuizSubmitResponse(questions.size(), quizResult);
        }
        throw new IllegalArgumentException("Quiz Or User not found.");
    }

    @Override
    public Quiz updateQuiz(QuizDTO quizDTO) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizDTO.getId());

        if(quizOptional.isPresent()){
            Quiz quiz = quizOptional.get();
            quiz.setName(quizDTO.getName());
            quiz.setActive(quizDTO.isActive());
            quiz.setDescription(quizDTO.getDescription());
            return quizRepository.save(quiz);
        }
        throw new IllegalArgumentException("Quiz Not found");
    }

    @Override
    public Quiz updateQuizStatus(QuizDTO quizDTO) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizDTO.getId());
        if(quizOptional.isPresent()){
            Quiz quiz = quizOptional.get();
            quiz.setActive(quizDTO.isActive());
            return quizRepository.save(quiz);
        }
        throw new IllegalArgumentException(("Quiz Not Found."));
    }

    @Override
    public QuizDTO getQuizDetailsById(Integer id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if(quizOptional.isPresent()){
            Quiz quiz = quizOptional.get();
            QuizDTO quizDTO = new QuizDTO();
            quizDTO.setActive(quiz.isActive());
            quizDTO.setId(quiz.getId());
            quizDTO.setName(quiz.getName());
            quizDTO.setCategoryName(quiz.getCategory().getName());
            quizDTO.setCategoryId(quiz.getCategory().getId());
            quizDTO.setDescription(quiz.getDescription());
            quizDTO.setQuestionDTOList(quiz.getQuestions().stream().map(question -> new QuestionDTO(question.getId(),question.getName(), quiz.getId(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4(), question.getAnswer())).collect(Collectors.toList()));

            return quizDTO;

        }
        throw new IllegalArgumentException("Quiz Not Found");
    }

    private boolean isPassed(Integer totalQuestions, Integer correctAnswers){
        return (correctAnswers / totalQuestions * 100 >= 33);
    }
}
