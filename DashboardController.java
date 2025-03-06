package com.example.file_share.controller;

import com.example.file_share.models.user; // Your user model
import com.example.file_share.service.UserService; // Service for user-related logic
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    /**
     * Show the dashboard for the logged-in user.
     *
     * @param authentication The authentication object containing user details.
     * @param model The model to pass data to the frontend.
     * @return The dashboard HTML page.
     */
    @GetMapping("/dashboard")
    public String showDashboard(Authentication authentication, Model model) {
        // Retrieve the currently logged-in user's username
        String username = authentication.getName();

        // Retrieve user details from the database
        user loggedInUser = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        // Add user-specific data to the model
        model.addAttribute("username", loggedInUser.getUsername());
        model.addAttribute("email", loggedInUser.getEmail());
        model.addAttribute("storageUsed", "50 MB"); // Example: dynamically fetch storage details (implement logic)

        return "main"; // Maps to src/main/resources/templates/main.html
    }
}