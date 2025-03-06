package com.example.file_share.controller;

import com.example.file_share.models.user;
import com.example.file_share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody user user) {
        // Check if the username or email is already taken
        if (userService.isUsernameTaken(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
        if (userService.isEmailTaken(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken.");
        }

        // Fix the role to 'user' before saving
        user.setrole("user");

        // Save the user
        userService.registerUser(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}