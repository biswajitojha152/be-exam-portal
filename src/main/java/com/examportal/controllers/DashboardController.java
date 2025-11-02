package com.examportal.controllers;

import com.examportal.dto.QuizTrailArgumentDTO;
import com.examportal.helper.SecurityContextHelper;
import com.examportal.models.ERole;
import com.examportal.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private SecurityContextHelper securityContextHelper;

    @GetMapping("/getDashboardSummary")
    public ResponseEntity<?> getDashboardSummary(){
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    @GetMapping("/getQuizTrail")
    public ResponseEntity<?> getQuizTrail(
            @ModelAttribute QuizTrailArgumentDTO quizTrailArgumentDTO
    ){
        String role = securityContextHelper.getCurrentUserRole();
        if(!Objects.equals(role, "ROLE_"+ERole.ADMIN.name())){
            quizTrailArgumentDTO.setSearchByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return ResponseEntity.ok(dashboardService.getQuizTrail(quizTrailArgumentDTO));
    }
}
