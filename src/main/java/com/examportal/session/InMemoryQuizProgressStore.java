package com.examportal.session;

import com.examportal.dto.QuizProgressDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class InMemoryQuizProgressStore {
    private final Map<String, QuizProgressDTO> quizProgressDTOMap = new HashMap<>();

    public void addUserWithQuizToMap(String username, QuizProgressDTO quizProgressDTO){
        quizProgressDTOMap.put(username, quizProgressDTO);
    }

    public QuizProgressDTO getQuizProgressForUser(String username){
        QuizProgressDTO quizProgressDTO =quizProgressDTOMap.get(username);
        if(quizProgressDTO != null){
            quizProgressDTO.setLastFetchedTime(Instant.now());
        }
        return quizProgressDTO;
    }

    public void handleQuestionChange(String username, Integer questionId, Long optionId){
        getQuizProgressForUser(username).getQuizProgressQuestionDTOList().stream().filter(question -> question.getId().equals(questionId)).findFirst().ifPresent(question -> question.getOptionDTOList().forEach(option -> option.setIsCorrect(option.getId().equals(optionId))));
    }

    public void handleNextQuestion(String  username, Integer questionId){
        QuizProgressDTO quizProgressDTO = getQuizProgressForUser(username);
        for(int i=0; i<quizProgressDTO.getQuizProgressQuestionDTOList().size(); i++){
            if(Objects.equals(questionId, quizProgressDTO.getQuizProgressQuestionDTOList().get(i).getId())){
                quizProgressDTO.getQuizProgressQuestionDTOList().get(i== quizProgressDTO.getQuizProgressQuestionDTOList().size() -1 ? 0 : i+1).setIsVisited(true);
            }
        }
    }

    public void handleVisitQuestion(String  username, Integer questionId){
        getQuizProgressForUser(username).getQuizProgressQuestionDTOList().stream().filter(question-> question.getId().equals(questionId)).findFirst().ifPresent(question-> question.setIsVisited(true));
    }

    public void handlePreviousQuestion(String username, Integer questionId){
        QuizProgressDTO quizProgressDTO = getQuizProgressForUser(username);
        for(int i=0; i<quizProgressDTO.getQuizProgressQuestionDTOList().size(); i++){
            if(Objects.equals(questionId, quizProgressDTO.getQuizProgressQuestionDTOList().get(i).getId())){
                quizProgressDTO.getQuizProgressQuestionDTOList().get(i== 0 ? 0 : i-1).setIsVisited(true);
            }
        }
    }
}
