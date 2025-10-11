package com.examportal.controllers;

import com.examportal.dto.QuestionChangeDTO;
import com.examportal.dto.QuestionQuizDTO;
import com.examportal.dto.QuizProgressDTO;
import com.examportal.dto.QuizProgressQuestionDTO;
import com.examportal.session.InMemoryQuizProgressStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class QuizWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private InMemoryQuizProgressStore inMemoryQuizProgressStore;

    @MessageMapping("/quiz/start")
    public void startQuiz(String message, Principal principal){
        messagingTemplate.convertAndSendToUser(principal.getName(),"/queue/reply", "Hello From Quiz App.");
    }

    @MessageMapping("/quiz/getQuizById")
    public void getQuizById(Integer questionId, Principal principal){
            inMemoryQuizProgressStore.handleVisitQuestion(principal.getName(), questionId);
            broadcastQuiz(principal.getName());
    }

    @MessageMapping("/question/handleChange")
    public void handleQuestionChange(QuestionChangeDTO questionChangeDTO, Principal principal){
        inMemoryQuizProgressStore.handleQuestionChange(principal.getName(), questionChangeDTO.getQuestionId(), questionChangeDTO.getOptionId());
        broadcastQuiz(principal.getName());
    }

    @MessageMapping("/question/handleNextQuestion")
    public void handleNextQuestion(QuestionChangeDTO questionChangeDTO, Principal principal){
        inMemoryQuizProgressStore.handleNextQuestion(principal.getName(), questionChangeDTO.getQuestionId());
        broadcastQuiz(principal.getName());
    }

    @MessageMapping("/question/handlePreviousQuestion")
    public void handlePreviousQuestion(QuestionChangeDTO questionChangeDTO, Principal principal){
        inMemoryQuizProgressStore.handlePreviousQuestion(principal.getName(), questionChangeDTO.getQuestionId());
        broadcastQuiz(principal.getName());
    }

    private void broadcastQuiz(String username){
        messagingTemplate.convertAndSendToUser(username, "/queue/getQuizById", inMemoryQuizProgressStore.getQuizProgressForUser(username));
    }
}
