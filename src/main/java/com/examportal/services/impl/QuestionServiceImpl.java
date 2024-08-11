package com.examportal.services.impl;

import com.examportal.dto.QuestionDTO;
import com.examportal.models.Question;
import com.examportal.models.Quiz;
import com.examportal.repository.QuestionRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return quiz.map(value -> questionRepository.save(
                new Question(null, questionDTO.getName(), questionDTO.getOption1(), questionDTO.getOption2(), questionDTO.getOption3(), questionDTO.getOption4(), questionDTO.getAnswer(), value)
        )).orElse(null);
    }

    @Override
    public List<QuestionDTO> getAllQuestions(Integer quizId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        return quiz.map(value -> value.getQuestions().stream().map(question -> new QuestionDTO(question.getId(), question.getName(), null,question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4(), null)).collect(Collectors.toList())).orElse(null);
    }
}
