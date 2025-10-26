package com.examportal.helper;

public class QuizAttemptStatusChecker {
    public static boolean isPassed(Integer totalQuestions, Integer correctAnswers){
        return (double)correctAnswers / totalQuestions * 100 >= 33;
    }
}
