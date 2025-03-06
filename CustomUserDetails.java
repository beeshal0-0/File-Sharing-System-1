package com.example.file_share.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final user user; // Reference to the `user` entity from your project

    public CustomUserDetails(user user) {
        this.user = user; // Wrap the `user` entity
    }

    /**
     * Retrieves the roles of the user and maps them as GrantedAuthority.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream() // Fetch roles from user
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())) // Add "ROLE_" prefix (Spring Security standard)
                .collect(Collectors.toList());
    }

    /**
     * Returns the user's password (encrypted).
     */
    @Override
    public String getPassword() {
        return user.getPassword(); // Fetch password from the `user` entity
    }

    /**
     * Returns the user's username.
     */
    @Override
    public String getUsername() {
        return user.getUsername(); // Fetch username from the `user` entity
    }

    /**
     * Indicates whether the user's account is expired (customize as needed).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Modify this behavior based on your application logic
    }

    /**
     * Indicates whether the user's account is locked (customize as needed).
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Modify this behavior based on your application logic
    }

    /**
     * Indicates whether the user's credentials are expired (customize as needed).
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modify this behavior based on your application logic
    }

    /**
     * Indicates whether the user is enabled (active). Customize as required.
     */
    @Override
    public boolean isEnabled() {
        return user.isActive(); // Map the `active` field in the user entity
    }

    /**
     * Exposes the user entity for other potential uses.
     */
    public user getUser() {
        return user; // Provide access to the original `user` entity
    }
}