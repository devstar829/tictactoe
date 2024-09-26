package com.telusko.simpleWebApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()  // Enable CORS
            .and()
            .csrf().disable()  // Disable CSRF if you're not using it
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .logout()
                .logoutUrl("/api/auth/logout")  // URL to trigger logout
                .logoutSuccessUrl("/")  // Redirect after logout, change to your preferred URL
                .invalidateHttpSession(true)  // Invalidate session
                .deleteCookies("JSESSIONID")  // Delete session cookie
                .permitAll();  // Allow everyone to access the logout functionality;  // Allow all requests for simplicity (adjust as needed)

        return http.build();
    }
}