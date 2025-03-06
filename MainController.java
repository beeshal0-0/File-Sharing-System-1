package com.example.file_share.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String IndexPage() {
        return "index"; // Loads index.html from templates
    }
}