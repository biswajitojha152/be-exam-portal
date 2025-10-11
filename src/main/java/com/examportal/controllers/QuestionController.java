package com.examportal.controllers;

import com.examportal.dto.QuestionDTO;
import com.examportal.dto.QuizDTO;
import com.examportal.dto.ResponseDTO;
import com.examportal.models.Question;
import com.examportal.payload.response.MessageResponse;
import com.examportal.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/saveQuestion")
    public ResponseEntity<MessageResponse> saveQuestion(@RequestBody QuestionDTO questionDTO){
        ResponseDTO<Question> questionResponseDTO = questionService.saveQuestion(questionDTO);
        if(questionResponseDTO.isSuccess()){
            return ResponseEntity.created(URI.create("question/"+questionResponseDTO.getData().getId())).body(new MessageResponse("Question added successfully."));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(questionResponseDTO.getMessage()));
    }

    @PostMapping("/importExcel")
    public ResponseEntity<MessageResponse> importQuestions(@RequestParam Integer quizId, @RequestParam MultipartFile file){
        ResponseDTO<List<Question>> questionListResponseDTO = questionService.importQuestions(quizId, file);
        if(questionListResponseDTO.isSuccess()){
            return ResponseEntity.created(URI.create("question/"+questionListResponseDTO.getData().get(0).getId())).body(new MessageResponse("Questions imported successfully."));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(questionListResponseDTO.getMessage()));
    }

    @GetMapping("/getAllQuestions/{quizId}")
    public ResponseEntity<QuizDTO> getAllQuestions(@PathVariable("quizId") Integer quizId){
        QuizDTO quizDTO = questionService.getAllQuestions(quizId);
        return ResponseEntity.ok(quizDTO);
    }
}
