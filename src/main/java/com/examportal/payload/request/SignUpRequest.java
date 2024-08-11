package com.examportal.payload.request;

import com.examportal.models.ERole;
import com.examportal.models.Role;

public class SignUpRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String profilePicture;
    private ERole role;

    public SignUpRequest() {
    }

    public SignUpRequest(
            String username,
            String firstName,
            String lastName,
            String email,
            String phone,
            String profilePicture,
            ERole role
    ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }
}
