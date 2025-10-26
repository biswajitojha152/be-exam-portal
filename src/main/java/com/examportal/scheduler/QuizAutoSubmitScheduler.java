package com.examportal.scheduler;

import com.examportal.dto.OptionDTO;
import com.examportal.dto.QuizDTO;
import com.examportal.dto.QuizProgressDTO;
import com.examportal.dto.QuizTrailDTO;
import com.examportal.helper.QuizAttemptStatusChecker;
import com.examportal.models.*;
import com.examportal.repository.QuizRepository;
import com.examportal.repository.QuizTrailRepository;
import com.examportal.repository.UserRepository;
import com.examportal.services.QuizService;
import com.examportal.session.InMemoryQuizProgressStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class QuizAutoSubmitScheduler {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    @Qualifier("autoSubmitExecutor")
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private InMemoryQuizProgressStore inMemoryQuizProgressStore;

    @Autowired
    private QuizService quizService;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Scheduled(fixedRate = 1000)
    public void checkAndAutoSubmit() throws InterruptedException {

        inMemoryQuizProgressStore.getQuizProgressDTOMap().forEach((key, value)->{
            executor.submit(()->{
                if(Duration.between(value.getQuizStartTime(), Instant.now()).getSeconds() == value.getQuizDurationInSeconds()){
                  QuizTrailDTO quizTrailDTO = quizService.handleQuizSubmit(key, value, Instant.now());
                    messagingTemplate.convertAndSendToUser(key, "/queue/autoSubmitQuiz", quizTrailDTO);
                }
            });
        });
    }
}
