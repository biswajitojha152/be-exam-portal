package com.examportal.dto.projection;

public interface DashboardDataUserProjection {
    Short getQuizzesAttempted();
    Double getAverageScore();
    Double getBestScore();
    Long getTotalTimeSpent();
}
