package com.examportal.services.impl;

import com.examportal.dto.ResponseDTO;
import com.examportal.dto.UserDTO;
import com.examportal.helper.EmailTemplateBuilder;
import com.examportal.helper.PasswordGenerator;
import com.examportal.models.EmailDetails;
import com.examportal.models.Role;
import com.examportal.models.User;
import com.examportal.payload.request.LoginRequest;
import com.examportal.payload.request.SignUpRequest;
import com.examportal.payload.response.JwtResponse;
import com.examportal.payload.response.MessageResponse;
import com.examportal.repository.RoleRepository;
import com.examportal.repository.UserRepository;
import com.examportal.security.jwt.JwtUtils;
import com.examportal.security.services.UserDetailsImpl;
import com.examportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public ResponseEntity<?> registerUserService(SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())){
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Username already exists.")
            );
        }else if(userRepository.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Email already exists.")
            );
        }

        Role userRole = roleRepository.findByName(
                        signUpRequest.getRole()
                )
                .orElseThrow(
                        ()-> new RuntimeException("Error: Role is not found.")
                );

        String password = PasswordGenerator.generatePassword(5);
        User user =new User(
                signUpRequest.getUsername(),
                encoder.encode(password),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                signUpRequest.getPhone(),
                signUpRequest.getProfilePicture(),
                Instant.now(),
                false,
                true,
                userRole
        );

        boolean credSendResult = emailService.sendMail(
                new EmailDetails(
                        signUpRequest.getEmail(),
                        EmailTemplateBuilder.generateRegisterEmail(signUpRequest.getFirstName(),
                                signUpRequest.getUsername(),
                                password
                        ),
                        "Credentials For Exam Portal."
                )
        );

        if(credSendResult){
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully! Credentials has been sent to the email id."));
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("Email does not exists, please try with a valid email."));
        }

    }

    @Override
    public ResponseEntity<?> loginUserService(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority).orElseThrow(()-> new IllegalArgumentException("Expected first item."))
                .replace("ROLE_", "");

        return ResponseEntity.ok(
            new JwtResponse(
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    jwt,
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    role,
                    userDetails.getProfilePicture(),
                    userDetails.getJoiningDate(),
                    userDetails.getIsDarkTheme()
            )
        );
    }

    @Override
    public List<UserDTO> getAllUserService() {
        String loggedInUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.forEach(user -> {
            boolean isLoggedInUser = user.getUsername().equals(loggedInUserUsername);
                UserDTO userDTO = new UserDTO(isLoggedInUser ? user.getId() : null, user.getUsername(), isLoggedInUser ? user.getFirstName() : null, isLoggedInUser ? user.getLastName() : null, isLoggedInUser ? user.getEmail() : null, isLoggedInUser ? user.getProfilePicture() : null, user.getRole().getName().name(), user.isActive());
                if(isLoggedInUser){
                    userDTOList.add(0, userDTO);
                }else {
                    userDTOList.add(userDTO);
                }

        });
        return userDTOList;
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public User getUserEntityByUsername(String username){
        return userRepository.findByUsername(
                        username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found."));
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public ResponseDTO<Void> toggleThem(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found."));
        user.setIsDarkTheme(!user.getIsDarkTheme());
        userRepository.save(user);
        return new ResponseDTO<>(true, "Theme updated successfully.", null);
    }
}
