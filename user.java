package com.example.file_share.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*; // JPA imports for ORM
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users") // Database table name
public class user implements UserDetails { // Implements UserDetails for Spring Security

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique user ID

    @Column(nullable = false)
    private String fullName; // User's full name

    @Column(nullable = false, unique = true)
    private String username; // Unique username

    @Column(nullable = false, unique = true)
    private String email; // Unique email

    @Column(nullable = false)
    private String phoneNumber; // Phone number

    @Column(nullable = false)
    private String password; // Encrypted password

    @Column(nullable = false)
    private boolean active = true; // True if account is active

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // Join table name
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key for User
            inverseJoinColumns = @JoinColumn(name = "role_id") // Foreign key for Role
    )
    private Set<role> roles = new HashSet<>(); // User roles (e.g., USER, ADMIN)

    // Relationships for file management
    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL)
    private List<FileEntity> uploadedFiles; // Files uploaded by this user

    @ManyToMany
    @JoinTable(
            name = "file_sharing", // Join table for shared files
            joinColumns = @JoinColumn(name = "shared_with_user_id"), // Foreign key for user
            inverseJoinColumns = @JoinColumn(name = "file_id") // Foreign key for file
    )
    private List<FileEntity> sharedFilesWithMe; // Files shared with this user

    // Constructor
    public user() {}

    // Parameterized constructor
    public user(String fullName, String username, String email, String phoneNumber, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username; // Implementing UserDetails method
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password; // Implementing UserDetails method
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<role> getRoles() {
        return roles;
    }

    public void setRoles(Set<role> roles) {
        this.roles = roles;
    }

    public List<FileEntity> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(List<FileEntity> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public List<FileEntity> getSharedFilesWithMe() {
        return sharedFilesWithMe;
    }

    public void setSharedFilesWithMe(List<FileEntity> sharedFilesWithMe) {
        this.sharedFilesWithMe = sharedFilesWithMe;
    }

    // UserDetails interface methods for Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role.getName()) // Prefix roles with "ROLE_"
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() { // Account expiration logic
        return true; // Always non-expired in this simple implementation
    }

    @Override
    public boolean isAccountNonLocked() { // Account lock logic
        return true; // Always non-locked in this implementation
    }

    @Override
    public boolean isCredentialsNonExpired() { // Credential expiration logic
        return true; // Always non-expired in this implementation
    }

    @Override
    public boolean isEnabled() { // User active status
        return active; // Return the 'active' field's value
    }
}