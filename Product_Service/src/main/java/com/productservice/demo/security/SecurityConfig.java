package com.productservice.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtValidator jwtValidator;

    public SecurityConfig(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (auth service calls)
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // USER can only read products
                        .requestMatchers("/api/v1/product/**").hasRole("USER")

                        // ADMIN can create/update/delete products
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtValidator),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
