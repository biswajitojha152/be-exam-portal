package com.examportal.services;

import com.examportal.dto.QuestionDTO;
import com.examportal.models.Question;

import java.util.List;

public interface QuestionService {
    public Question saveQuestion(QuestionDTO questionDTO);
    public List<QuestionDTO> getAllQuestions(Integer quizId);
}
