package com.examportal.security.services;

import com.examportal.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final Collection<? extends  GrantedAuthority> authorities;
    private final boolean isActive;
    private final String profilePicture;

    public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean isActive, String profilePicture) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
        this.profilePicture = profilePicture;
    }

    public static UserDetailsImpl build(User user){
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().name()));
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorities, user.isActive(), user.getProfilePicture());
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

}
