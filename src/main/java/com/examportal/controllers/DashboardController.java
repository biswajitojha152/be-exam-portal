package com.examportal.controllers;

import com.examportal.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/getDashboardSummary")
    public ResponseEntity<?> getDashboardSummary(){
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }
}
