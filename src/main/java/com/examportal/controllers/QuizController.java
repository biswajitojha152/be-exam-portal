package com.examportal.controllers;

import com.examportal.dto.QuizDTO;
import com.examportal.models.Quiz;
import com.examportal.payload.response.MessageResponse;
import com.examportal.services.QuizService;
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
    public ResponseEntity<List<QuizDTO>> getAllQuiz(@RequestParam(required = false) Integer categoryId) {

        return ResponseEntity.ok(quizService.getAllQuiz(categoryId));
    }

    @PostMapping("/saveQuiz")
    public ResponseEntity<MessageResponse> saveQuiz(@RequestBody QuizDTO quizDTO) {
        Quiz quizEntity = quizService.saveQuiz(quizDTO);
        if (quizEntity == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Quiz name already exists"));
        }
        return ResponseEntity.created(URI.create("quiz/" + quizEntity.getId())).body(new MessageResponse("Quiz added Successfully"));
    }

    @GetMapping("/getQuizById/{quizId}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable("quizId") Integer quizId){
        return ResponseEntity.ok(quizService.getQuizDetailsById(quizId));
    }

    @PutMapping("/updateQuiz")
    public ResponseEntity<MessageResponse> updateQuiz(@RequestBody QuizDTO quizDTO){
        Quiz result =quizService.updateQuiz(quizDTO);
        if(result == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Quiz name already exists."));
        }

        return ResponseEntity.ok(new MessageResponse("Quiz updated successfully."));
    }

    @PutMapping("/updateQuizStatus")
    public ResponseEntity<MessageResponse> updateQuizStatus(@RequestBody QuizDTO quizDTO){
        quizService.updateQuizStatus(quizDTO);
        return ResponseEntity.ok(new MessageResponse("Status updated successfully"));
    }

    @PostMapping("/submitQuiz")
    public ResponseEntity<?> submitQuiz(@RequestBody QuizDTO quizDTO){
        return ResponseEntity.ok(quizService.submitQuiz(quizDTO));
    }

}
