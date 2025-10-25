package com.examportal.controllers;

import com.examportal.dto.UserDTO;
import com.examportal.payload.response.MessageResponse;
import com.examportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUserService());
    }

    @PostMapping("/toggleTheme")
    public ResponseEntity<MessageResponse> toggleTheme() {
        return ResponseEntity.ok(new MessageResponse(userService.toggleThem(SecurityContextHolder.getContext().getAuthentication().getName()).getMessage()));
    }
}
