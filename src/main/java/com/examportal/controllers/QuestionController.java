package com.examportal.controllers;

import com.examportal.dto.QuestionDTO;
import com.examportal.models.Question;
import com.examportal.payload.response.MessageResponse;
import com.examportal.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/saveQuestion")
    public ResponseEntity<MessageResponse> saveQuestion(@RequestBody QuestionDTO questionDTO){
        Question question = questionService.saveQuestion(questionDTO);
        if(question==null){
            throw new IllegalArgumentException("Quiz doesn't exists.");
        }
        return ResponseEntity.created(URI.create("question/"+question.getId())).body(new MessageResponse("Question added successfully."));
    }

    @GetMapping("/getAllQuestions/{quizId}")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(@PathVariable("quizId") Integer quizId){
        List<QuestionDTO> questionDTOList = questionService.getAllQuestions(quizId);
        return ResponseEntity.ok(questionDTOList);
    }
}
