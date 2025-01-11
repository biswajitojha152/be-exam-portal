package com.examportal.services;

import com.examportal.dto.DashboardData;
import com.examportal.dto.PaginatedResponse;
import com.examportal.dto.QuizTrailArgumentDTO;
import com.examportal.dto.QuizTrailDTO;

public interface DashboardService {
    public DashboardData getDashboardSummary();

    public PaginatedResponse<QuizTrailDTO> getQuizTrail(QuizTrailArgumentDTO quizTrailArgumentDTO);
}
