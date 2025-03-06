package com.example.file_share.controller;

import com.example.file_share.models.user; // Import the correct lowercase model
import com.example.file_share.service.UserService; // Import the service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;



    // Endpoint to register a new user
    @PostMapping("/register")
    public ResponseEntity<user> registerUser(@RequestBody user newUser) {
        try {
            user savedUser = userService.registerUser(newUser); // Call the service method
            return ResponseEntity.ok(savedUser); // Return the saved user as the response
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle duplicate username
        }
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(
                loginRequest.getUsername(), loginRequest.getPassword()
        );

        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // Class to Handle Login Request Data
    private static class LoginRequest {
        private String username;
        private String password;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}