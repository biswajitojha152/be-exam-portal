package com.examportal.services.impl;

import com.examportal.dto.QuestionDTO;
import com.examportal.models.ERole;
import com.examportal.models.Question;
import com.examportal.models.Quiz;
import com.examportal.repository.QuestionRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Question saveQuestion(QuestionDTO questionDTO) {
        Optional<Quiz> quiz = quizRepository.findById(questionDTO.getQuizId());
            return quiz.map(val-> questionRepository.save(
                    new Question(null, questionDTO.getName(), questionDTO.getOption1(), questionDTO.getOption2(), questionDTO.getOption3(), questionDTO.getOption4(), questionDTO.getAnswer(), val))).orElseThrow(()-> new IllegalArgumentException("Quiz not found with Quiz ID: "+ questionDTO.getQuizId()));

    }

    @Override
    public List<QuestionDTO> getAllQuestions(Integer quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(quizOptional.isPresent()){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            List<Question> questions = questionRepository.findByQuiz(quizOptional.get(), Sort.by(Sort.Direction.DESC, "id"));

            if(authentication.getAuthorities().stream().anyMatch(a-> a.getAuthority().equals("ROLE_"+ ERole.ADMIN.name()))){
                return questions.stream().map(question -> new QuestionDTO(question.getId(), question.getName(), null,question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4(), question.getAnswer())).collect(Collectors.toList());
            }
            return questions.stream().map(question -> new QuestionDTO(question.getId(), question.getName(), null,question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4(), null)).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Quiz Not Found With Quiz Id: "+quizId);
    }
}
