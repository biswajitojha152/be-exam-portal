package com.examportal.helper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextHelper {
    public String getCurrentUserRole(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().orElseThrow(()-> new RuntimeException("Role not found at zero index.")).getAuthority();
    }
}
