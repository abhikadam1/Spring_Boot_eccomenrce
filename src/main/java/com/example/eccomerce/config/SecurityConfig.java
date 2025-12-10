package com.example.eccomerce.config;

import com.example.eccomerce.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ⚠️ NOTE: In a real web app, remove AbstractHttpConfigurer::disable to enable CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // 1. Define Authorization Rules
                .authorizeHttpRequests(authorize -> authorize
                        // Allow all static resources, registration, and the login page
                        .requestMatchers("/login", "/register/**", "/css/**", "/js/**", "/images/**").permitAll()

                        // Role-based access
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // ALL OTHER requests MUST be authenticated, including the Home Page ("/")
                        .anyRequest().authenticated()
                )

                // 2. Configure Form Login
                .formLogin(form -> form
                        .loginPage("/login")        // Tells Spring Security where the custom login form is
                        .loginProcessingUrl("/login") // The default URL for form submission (POST)
                        .defaultSuccessUrl("/", true) // Redirect to home page after success
                        .failureUrl("/login?error") // Redirect back to login on failure
                )

                // 3. Configure Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Redirect to login page with logout parameter
                        .permitAll()
                );

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Only disable for simple demos/APIs, keep enabled for production web MVC
//                .authorizeHttpRequests(authorize -> authorize
//                        // Public access: Home, Login, Registration, Products (view only)
//                        .requestMatchers( "/register/**", "/login", "/products", "/css/**", "/js/**", "/images/**").permitAll()
//
//                        // The login path MUST be permitted. Note: Use "/login" not "/login**"
//                        .requestMatchers("/login").permitAll()
//
//                        // Make the Home Page ('/') require authentication if it's a protected dashboard
//                        .requestMatchers("/").authenticated() // 👈 CHANGE THIS
//
//                        // ⭐️ CONCEPT: Authorization (Role-based access)
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only ADMIN role can access
//                        .requestMatchers("/order/place").hasRole("USER") // Only USER role can place orders
//
//                        // All other requests must be authenticated
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
////                        .permitAll()
//                        .defaultSuccessUrl("/", true)
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );
//
//        return http.build();
//    }
}