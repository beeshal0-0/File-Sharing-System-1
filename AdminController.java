package com.example.file_share.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    // Serves the admin login page (adminlogin.html)
    @GetMapping("/adminlogin")
    public String adminLoginPage() {
        return "adminlogin"; // Maps to src/main/resources/templates/adminlogin.html
    }

    // Handles admin login form submission
    @PostMapping("/admin/authenticate")
    public String authenticateAdmin(@RequestParam String username, @RequestParam String password) {
        // Hardcoded authentication logic for admin
        if (username.equals("beeshal") && password.equals("beeshal123")) {
            return "redirect:/admin/dashboard"; // Redirect to admin dashboard
        }
        return "redirect:/adminlogin?error=true"; // Reload login page on failure
    }
}