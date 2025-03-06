package com.example.file_share.config;

import com.example.file_share.models.CustomUserDetails;
import com.example.file_share.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for development, consider enabling for production
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/dashboard", "/main.html") // Protect dashboard access
                        .authenticated() // Require authentication to access these endpoints
                        .requestMatchers("/users/**", "/roles/**")
                        .hasRole("ADMIN") // Restrict these to ADMIN role
                        .requestMatchers("/files/**")
                        .hasRole("USER") // Restrict `files/**` to USER role
                        .anyRequest()
                        .permitAll() // Allow public access to all other endpoints
                )
                // Configure form-based login
                .formLogin(form -> form
                        .loginPage("/userlogin") // Define custom login page
                        .loginProcessingUrl("/perform_login") // Define endpoint to process login
                        .defaultSuccessUrl("/dashboard") // Redirect to dashboard (main.html) after login
                        .failureUrl("/userlogin?error=true") // Redirect on login failure
                        .permitAll() // Allow everyone to access the login page
                )
                // Configure logout
                .logout(logout -> logout
                        .logoutUrl("/logout") // Define logout URL
                        .logoutSuccessUrl("/userlogin?logout=true") // Redirect to login page after logout
                        .deleteCookies("JSESSIONID") // Clear session cookies upon logout
                        .invalidateHttpSession(true) // Invalidate the session
                        .permitAll() // Allow access to logout for all users
                )
                // Exception handling (redirect unauthorized users to login)
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403") // Redirect to a custom "Access Denied" page
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt is used for encoding user passwords
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Load user details using UserService
        return username -> userService.findByUsername(username)
                .map(user -> new CustomUserDetails(user)) // Wrap the user in the CustomUserDetails object
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Configure authentication provider with userDetailsService and password encoder
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // AuthenticationManager bean to handle authentication processes
        return authConfig.getAuthenticationManager();
    }
}