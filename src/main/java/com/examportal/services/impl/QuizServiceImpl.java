package com.examportal.services.impl;

import com.examportal.dto.QuizDTO;
import com.examportal.dto.QuizSubmitResponse;
import com.examportal.models.*;
import com.examportal.repository.CategoryRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.repository.QuizTrailRepository;
import com.examportal.repository.UserRepository;
import com.examportal.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<QuizDTO> getAllQuiz(Integer categoryId) {
        if(categoryId == null){
            return quizRepository.getAllQuiz();
        }
        Optional<Category> categoryOptional =  categoryRepository.findById(categoryId);

        if(categoryOptional.isEmpty()){
            return null;
        }

        List<Quiz> quizList= categoryOptional.get().getQuiz();

        List<QuizDTO> quizDTOList= new ArrayList<>();
        quizList.forEach(quiz->{
            QuizDTO quizDTO = new QuizDTO();
             quizDTO.setId(quiz.getId());
             quizDTO.setName(quiz.getName());
             quizDTOList.add(quizDTO);
        });
        return quizDTOList;
    }

    @Override
    public Quiz saveQuiz(QuizDTO quizDTO) {
        if(quizRepository.existsByName(quizDTO.getName())){
            return null;
        }

        Optional<Category> category = categoryRepository.findById(quizDTO.getCategoryId());
        if(category.isPresent()){
            return quizRepository.save(new Quiz(null, quizDTO.getName(), category.get(), null, null));
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


            quizTrailRepository.save(new QuizTrail(null, quiz.get(), user.get(), questions.size(), attemptedQuestions, quizResult, new Date()));

            return new QuizSubmitResponse(questions.size(), quizResult);
        }
        throw new IllegalArgumentException("Quiz Or User not found.");
    }
}
