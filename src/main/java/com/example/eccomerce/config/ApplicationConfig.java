package com.example.eccomerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableAsync // ⭐️ CONCEPT: Multithreading (Allows @Async annotation)
@EnableMethodSecurity(prePostEnabled = true) // ⭐️ CONCEPT: Authorization (Enables @PreAuthorize)
public class ApplicationConfig {
    // This class is primarily for enabling global Spring features
}
