package com.examportal.security.services;

import com.examportal.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final Collection<? extends  GrantedAuthority> authorities;
    private final boolean isActive;
    private final String profilePicture;
    private final boolean isDarkTheme;

    public UserDetailsImpl(String firstName, String lastName, String username, String password, Collection<? extends GrantedAuthority> authorities, boolean isActive, String profilePicture, boolean isDarkTheme) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
        this.profilePicture = profilePicture;
        this.isDarkTheme = isDarkTheme;
    }

    public static UserDetailsImpl build(User user){
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().name()));
        return new UserDetailsImpl(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), authorities, user.isActive(), user.getProfilePicture(), user.getIsDarkTheme());
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public boolean getIsDarkTheme() {
        return isDarkTheme;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
