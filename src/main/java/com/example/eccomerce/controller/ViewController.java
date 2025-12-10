package com.example.eccomerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    /**
     * Handles the protected Home page ("/")
     * Accessing this requires authentication.
     * If unauthenticated, Spring Security redirects to /login.
     */
    @GetMapping("/")
    public String home() {
        // This view is protected. If unauthenticated, SecurityConfig redirects to /login.
        return "home";
    }

    /**
     * Displays the custom login form.
     * NOTE: We only map the GET request.
     * The POST request to /login is handled automatically by Spring Security.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handles the public registration page.
     */
    @GetMapping("/register")
    public String register() {
        return "registration";
    }

    // You would add other application controllers here (e.g., /products, /admin/dashboard)
}