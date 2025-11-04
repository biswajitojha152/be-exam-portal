package com.examportal.services;

import com.examportal.dto.DashboardDataAdmin;
import com.examportal.dto.PaginatedResponse;
import com.examportal.dto.QuizTrailArgumentDTO;
import com.examportal.dto.QuizTrailDTO;
import com.examportal.dto.projection.DashboardDataUserProjection;

public interface DashboardService {
    public DashboardDataAdmin getDashboardSummaryAdmin();

    public DashboardDataUserProjection getDashboardSummaryUser();

    public PaginatedResponse<QuizTrailDTO> getQuizTrail(QuizTrailArgumentDTO quizTrailArgumentDTO);
}
