package com.examportal.services;

import com.examportal.payload.request.LoginRequest;
import com.examportal.payload.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<?> registerUserService(SignUpRequest signUpRequest);
    public ResponseEntity<?> loginUserService(LoginRequest loginRequest);
}
