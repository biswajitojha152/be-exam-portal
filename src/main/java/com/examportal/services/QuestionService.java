package com.examportal.services;

import com.examportal.dto.QuestionDTO;
import com.examportal.dto.QuizDTO;
import com.examportal.dto.ResponseDTO;
import com.examportal.models.Question;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface QuestionService {
    public ResponseDTO<Question> saveQuestion(QuestionDTO questionDTO);
    public ResponseDTO<List<Question>> importQuestions(Integer quizId, MultipartFile file);
    public QuizDTO getAllQuestions(Integer quizId);
}
