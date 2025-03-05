package com.javaFeatures.employeeAPI.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class EmployeeSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChainEmployee(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/employees").permitAll()  // Public endpoints accessible to everyone
                                .requestMatchers("/h2-console/**").permitAll() // Allow H2 Console
                                .requestMatchers("/employees/**")
                                .hasRole("ADMIN")     // Restricted to HR / Manager role
                                .anyRequest().authenticated()  // All other requests need authentication
                )
                .formLogin(Customizer.withDefaults())  // Enables form-based authentication
                .httpBasic(Customizer.withDefaults());  // Enables Basic Auth

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); // Disable CSRF for H2
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // Allow H2 Console Frames


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Secure password encoding
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
