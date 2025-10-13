package com.examportal.services;

import com.examportal.dto.*;
import com.examportal.models.Quiz;

import java.util.List;

public interface QuizService {
    PaginatedResponse<QuizDTO> getAllQuiz(Integer pageNo, Integer pageSize,Integer categoryId, String searchInput);

    ResponseDTO<Quiz> saveQuiz(QuizDTO quizDTO);

    ResponseDTO<Quiz> updateQuiz(QuizDTO quizDTO);

    QuizSubmitResponse submitQuiz();

    ResponseDTO<List<Quiz>> updateQuizzesStatus(UpdateEntitiesStatusDTO<Integer> updateEntitiesStatusDTO);

    QuizDTO getQuizDetailsById(Integer id);

    QuizIdsWithQuizCountDTO getQuizIdsWithQuizCount(Integer categoryId, String searchInput);

    EntityUpdateTrailDTO<QuizDTO> getQuizUpdateTrailDTO(Integer entityId);

    List<EntitiesStatusUpdateTrailDTO<QuizDTO>> getQuizzesStatusUpdateTrailDTO();

    QuizInstructionDTO getQuizInstructions(Integer quizId);

    ResponseDTO<QuizStartResponseDTO> startQuizService(Integer quizId);

}
