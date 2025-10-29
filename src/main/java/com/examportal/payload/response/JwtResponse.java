package com.examportal.payload.response;

import java.time.Instant;

public class JwtResponse {
    private String firstName;
    private String lastName;
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String role;
    private String profileUrl;
    private Instant joiningDate;
    private boolean isDarkTheme;

    public JwtResponse() {
    }

    public JwtResponse(String firstName, String lastName, String token, String username, String email, String role, String profileUrl, Instant joiningDate, boolean isDarkTheme) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
        this.profileUrl= profileUrl;
        this.joiningDate = joiningDate;
        this.isDarkTheme=isDarkTheme;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public Instant getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Instant joiningDate) {
        this.joiningDate = joiningDate;
    }

    public boolean getIsDarkTheme() {
        return isDarkTheme;
    }

    public void setIsDarkTheme(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
    }
}
