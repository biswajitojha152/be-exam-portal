package com.examportal.dto.projection;

public interface CategoryProjection {
    Integer getId();
    String getName();
    String getDescription();
    boolean getIsActive();
    Long getTotalQuizCount();
    Long getActiveQuizCount();
    Long getInActiveQuizCount();
}
