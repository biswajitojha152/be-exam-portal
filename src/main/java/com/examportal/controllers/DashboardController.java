package com.examportal.controllers;

import com.examportal.dto.QuizTrailArgumentDTO;
import com.examportal.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/getDashboardSummary")
    public ResponseEntity<?> getDashboardSummary(){
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    @GetMapping("/getQuizTrail")
    public ResponseEntity<?> getQuizTrail(
            @ModelAttribute QuizTrailArgumentDTO quizTrailArgumentDTO
    ){
        return ResponseEntity.ok(dashboardService.getQuizTrail(quizTrailArgumentDTO));
    }
}
