package com.examportal.dto.projection;

public interface QuizProjectionWithQuestionCount {
    Integer getId();
    String getName();
    Integer getCategoryId();
    String getCategoryName();
    String getDescription();
    Long getTotalQuestionCount();
    Long getActiveQuestionCount();
    Long getInActiveQuestionCount();
    Byte getAttemptableCount();
    Byte getDuration();
    boolean getIsActive();
}
