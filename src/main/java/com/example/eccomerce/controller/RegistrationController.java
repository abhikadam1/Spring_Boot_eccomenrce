package com.example.eccomerce.controller;

import com.example.eccomerce.model.User;
import com.example.eccomerce.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Dependency Injection via constructor
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Handles GET request to display the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Must add an empty User object to the model for the Thymeleaf form to bind to
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "registration"; // Maps to registration.html
    }

    // Handles POST request for form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               RedirectAttributes redirectAttributes,
                               Model model, HttpServletRequest request) {
        System.out.println(request.getSession().getId());
        System.out.println(request.getSession().getAttribute("first_name"));
        System.out.println(request.getSession().getAttribute("password"));
        System.out.println(request.getSession().getAttribute("first_name"));
        System.out.println(user.toString());
        System.out.println(user.getFirstName() + " first name");
        // 1. Validation Check: Email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already registered!");
            // Return to the form with the error message
            return "registration";
        }

        try {
            // 2. Encode Password (CRITICAL for Spring Security)
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // 3. Set Role (e.g., default user role)
            user.setRole("ROLE_USER");
//            user.setRole("ROLE_ADMIN");

            // 4. Save User to Database
            userRepository.save(user);

            // 5. Success: Redirect to login with a success message
            redirectAttributes.addFlashAttribute("message", "Registration successful! You can now log in.");
            return "redirect:/login";

        } catch (Exception e) {
            // 6. Handle any database or runtime error during save
            System.err.println("Registration failed: " + e.getMessage());
            model.addAttribute("error", "An unexpected error occurred during registration. Please check server logs.");

            // Keep the user object so form data is not lost
            model.addAttribute("user", user);
            return "registration";
        }
    }

    // Simple mapping for the login page (needed for GET /login to work)
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}