package com.examportal.services;

import com.examportal.models.EmailDetails;

public interface EmailService {
    boolean sendMail(EmailDetails details);
}
