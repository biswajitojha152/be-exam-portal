package com.examportal.controllers;

import com.examportal.helper.FileValidator;
import com.examportal.models.ERole;
import com.examportal.payload.request.LoginRequest;
import com.examportal.payload.request.SignUpRequest;
import com.examportal.services.FileService;
import com.examportal.services.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUserService(loginRequest);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(
            @RequestParam @NotBlank String username,
            @RequestParam @NotBlank String firstName,
            @RequestParam @NotBlank String lastName,
            @RequestParam @NotBlank @Email String email,
            @RequestParam @NotBlank @Size(min = 10, max = 10) String phone,
            @RequestParam @NotBlank String role,
            @RequestParam(required = false) MultipartFile file
    ){
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(username);
        signUpRequest.setFirstName(firstName);
        signUpRequest.setLastName(lastName);
        signUpRequest.setEmail(email);
        signUpRequest.setPhone(phone);
        signUpRequest.setRole(ERole.valueOf(role));



        if(file != null){
            if(!FileValidator.isImageType(Objects.requireNonNull(file.getContentType()))){
                throw  new RuntimeException("File is not image type.");
            }
            String fileName = fileService.storeFile(file);
            signUpRequest.setProfilePicture(fileName);
        }

        return userService.registerUserService(signUpRequest);
    }

}
