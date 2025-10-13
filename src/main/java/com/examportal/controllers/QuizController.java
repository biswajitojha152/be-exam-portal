package com.examportal.controllers;

import com.examportal.dto.*;
import com.examportal.models.Quiz;
import com.examportal.payload.response.MessageResponse;
import com.examportal.services.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("/getAllQuiz")
    public ResponseEntity<PaginatedResponse<QuizDTO>> getAllQuiz(@RequestParam(required = false, defaultValue = "0") Integer pageNo, @RequestParam(required = false, defaultValue = "0") Integer pageSize, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String searchInput) {
        return ResponseEntity.ok(quizService.getAllQuiz(pageNo, pageSize, categoryId, searchInput));
    }

    @PostMapping("/saveQuiz")
    public ResponseEntity<MessageResponse> saveQuiz(@RequestBody @Valid QuizDTO quizDTO) {
        ResponseDTO<Quiz> quizSaveResponse = quizService.saveQuiz(quizDTO);
        if(quizSaveResponse.isSuccess()) {
            return ResponseEntity.created(URI.create("quiz/" + quizSaveResponse.getData().getId())).body(new MessageResponse("Quiz added Successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(quizSaveResponse.getMessage()));
    }

    @PutMapping("/updateQuiz")
    public ResponseEntity<MessageResponse> updateQuiz(@RequestBody @Valid QuizDTO quizDTO){
        ResponseDTO<Quiz> quizResponseDTO = quizService.updateQuiz(quizDTO);
        if(quizResponseDTO.isSuccess()){
            return ResponseEntity.ok(new MessageResponse(quizResponseDTO.getMessage()));
        }else {
            return ResponseEntity.badRequest().body(new MessageResponse(quizResponseDTO.getMessage()));
        }
    }

    @GetMapping("/getQuizById/{quizId}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable("quizId") Integer quizId){
        return ResponseEntity.ok(quizService.getQuizDetailsById(quizId));
    }

    @PutMapping("/updateQuizzesStatus")
    public ResponseEntity<MessageResponse> updateQuizzesStatus(@RequestBody @Valid UpdateEntitiesStatusDTO<Integer> updateEntitiesStatusDTO){
        ResponseDTO<List<Quiz>> quizzesUpdateStatusResponse = quizService.updateQuizzesStatus(updateEntitiesStatusDTO);
        if(quizzesUpdateStatusResponse.isSuccess()){
            return ResponseEntity.ok(new MessageResponse(quizzesUpdateStatusResponse.getMessage()));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(quizzesUpdateStatusResponse.getMessage()));
    }

    @PostMapping("/submitQuiz")
    public ResponseEntity<?> submitQuiz(){
        return ResponseEntity.ok(quizService.submitQuiz());
    }

    @GetMapping("/getQuizIdsWithQuizCount")
    public ResponseEntity<QuizIdsWithQuizCountDTO> getQuizIdsWithQuizCount(@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String searchInput){
        return ResponseEntity.ok(quizService.getQuizIdsWithQuizCount(categoryId, searchInput));
    }

    @GetMapping("/getQuizUpdateAuditLog")
    public ResponseEntity<EntityUpdateTrailDTO<QuizDTO>> getQuizUpdateAuditLog(@RequestParam Integer quizId){
        return ResponseEntity.ok(quizService.getQuizUpdateTrailDTO(quizId));
    }

    @GetMapping("/getQuizStatusUpdateAuditLog")
    public ResponseEntity<List<EntitiesStatusUpdateTrailDTO<QuizDTO>>> getQuizzesStatusAuditLog(){
        return ResponseEntity.ok(quizService.getQuizzesStatusUpdateTrailDTO());
    }

    @GetMapping("/getQuizInstructions")
    public ResponseEntity<QuizInstructionDTO> getQuizInstructions(@RequestParam Integer quizId){
        return ResponseEntity.ok().body(quizService.getQuizInstructions(quizId));
    }

    @PostMapping("/startQuiz")
    public ResponseEntity<?> startQuiz(@RequestBody QuizDTO quizDTO){
        ResponseDTO<QuizStartResponseDTO> result = quizService.startQuizService(quizDTO.getId());
        if(result.isSuccess()) {
            return ResponseEntity.ok().body(result.getData());
        }
        return ResponseEntity.badRequest().body(new MessageResponse(result.getMessage()));
    }
}
