package com.examportal.services.impl;

import com.examportal.dto.DashboardData;
import com.examportal.repository.CategoryRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.repository.UserRepository;
import com.examportal.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public DashboardData getDashboardSummary() {
        return new DashboardData(userRepository.count(), quizRepository.count(), categoryRepository.count());
    }
}
