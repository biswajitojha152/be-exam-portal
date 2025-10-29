package com.examportal.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 55, nullable = false, unique = true)
    private String username;

    @Column(length = 55,nullable = false)
    private String firstName;

    @Column(length = 55, nullable = false)
    private String lastName;

    @Column(length = 55, nullable = false, unique = true)
    private String email;

    @Column(length = 15, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    private String profilePicture;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Role role;

    private Instant joiningDate;

    private boolean isDarkTheme;

    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<QuizTrail> quizTrails;

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<AuditLog> auditLogs;

    public User() {
    }

    public User(
            String username,
            String password,
            String firstName,
            String lastName,
            String email,
            String phone,
            String profilePicture,
            Instant joiningDate,
            boolean isDarkTheme,
            boolean isActive,
            Role role
    ) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.joiningDate = joiningDate;
        this.isDarkTheme = isDarkTheme;
        this.isActive = isActive;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Instant getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Instant joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean getIsDarkTheme() {
        return isDarkTheme;
    }

    public void setIsDarkTheme(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<QuizTrail> getQuizTrails() {
        return quizTrails;
    }

    public void setQuizTrails(List<QuizTrail> quizTrails) {
        this.quizTrails = quizTrails;
    }

    public List<AuditLog> getAuditLogs() {
        return auditLogs;
    }

    public void setAuditLogs(List<AuditLog> auditLogs) {
        this.auditLogs = auditLogs;
    }
}
