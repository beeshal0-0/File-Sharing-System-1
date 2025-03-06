package com.example.file_share.service;

import com.example.file_share.models.user;
import com.example.file_share.repositories.UserRepository; // Repository for user persistence
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor-based dependency injection
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean isUsernameTaken(String username) {
        Optional<user> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }

    public boolean isEmailTaken(String email) {
        Optional<user> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    public user registerUser(user user) {
        // Save the user after validations
        return userRepository.save(user);
    }
}

/**
     * Save a new user with encrypted password.
     *
     * @param user The user to save.
     * @return The saved user.
     */
    public user saveUser(user user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password before saving
        return userRepository.save(user); // Save the user in the repository
    }

    /**
     * Register a new user after performing validation.
     *
     * @param user The user to register.
     * @return The registered user.
     * @throws IllegalArgumentException when username or email is already taken.
     */
    public user registerUser(user user) {
        // Check if the username is already taken
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("The username '" + user.getUsername() + "' is already taken.");
        }

        // Check if the email is already taken (if email needs to be unique)
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The email '" + user.getEmail() + "' is already taken.");
        }

        // Encrypt the password and save the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Authenticate a user by their username and password.
     *
     * @param username The username to authenticate.
     * @param password The plain-text password to verify.
     * @return true if user exists and password matches; false otherwise.
     */
    public boolean authenticateUser(String username, String password) {
        // Retrieve the user by username
        Optional<user> optionalUser = userRepository.findByUsername(username);

        // Check if user exists and verify their password
        return optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword());
    }

    /**
     * Retrieve a user by ID.
     *
     * @param id The user ID.
     * @return An Optional containing the user, if found.
     */
    public Optional<user> getUserById(Long id) {
        return UserRepository.findById(id);
    }

    /**
     * Retrieve all users.
     *
     * @return A list of all users.
     */
    public List<user> getAllUsers() {
        return UserRepository.findAll();
    }

    /**
     * Update an existing user.
     *
     * @param user The updated user object.
     * @return The updated user.
     */
    public user updateUser(user user) {
        return UserRepository.save(user);
    }

    /**
     * Delete a user by ID.
     *
     * @param id The ID of the user to delete.
     */
    public void deleteUser(Long id) {
        if (!UserRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID '" + id + "' does not exist.");
        }
        UserRepository.deleteById(id);
    }

    /**
     * Find a user by username.
     *
     * @param username The username to search for.
     * @return An Optional containing the user, if found.
     */
    public Optional<user> findByUsername(String username) {
        return UserRepository.findByUsername(username);
    }

    /**
     * Check if a username or email is taken.
     *
     * @param user The user object to validate.
     * @throws IllegalArgumentException if username or email is already taken.
     */
    public void validateUserUniqueness(user user) {
        // Check username
        if (UserRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("The username '" + user.getUsername() + "' is already taken.");
        }

        // Check email
        if (UserRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The email '" + user.getEmail() + "' is already taken.");
        }
    }
public Long getTotalStorageUsed(String username) {
    user user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return user.getUploadedFiles().stream()
            .mapToLong(FileEntity::getFileSize)
            .sum();
}
}

