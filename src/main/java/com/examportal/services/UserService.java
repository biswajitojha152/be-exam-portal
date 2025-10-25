package com.examportal.services;

import com.examportal.dto.ResponseDTO;
import com.examportal.dto.UserDTO;
import com.examportal.models.User;
import com.examportal.payload.request.LoginRequest;
import com.examportal.payload.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<?> registerUserService(SignUpRequest signUpRequest);
    ResponseEntity<?> loginUserService(LoginRequest loginRequest);
    List<UserDTO> getAllUserService();
    User getUserEntityByUsername(String username);
    ResponseDTO<Void> toggleThem(String username);
}
