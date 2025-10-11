package com.examportal.dto.projection;

public interface QuizProjection {
    Integer getId();
    String getName();
    String getCategoryName();
    String getDescription();
    Byte getAttemptableCount();
    Byte getDuration();
}
