package com.examportal.services;

import com.examportal.dto.QuizDTO;
import com.examportal.dto.QuizSubmitResponse;
import com.examportal.models.Quiz;

import java.util.List;

public interface QuizService {
    public List<QuizDTO> getAllQuiz(Integer categoryId);

    public Quiz saveQuiz(QuizDTO quizDTO);

    public QuizSubmitResponse submitQuiz(QuizDTO quizDTO);

    public Quiz updateQuiz(QuizDTO quizDTO);

    public Quiz updateQuizStatus(QuizDTO quizDTO);

    public QuizDTO getQuizDetailsById(Integer id);
}
