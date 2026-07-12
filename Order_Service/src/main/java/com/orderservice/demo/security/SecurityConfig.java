package com.orderservice.demo.security;

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
                        // Public endpoints
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // USER endpoints
                        .requestMatchers("/api/v1/orders/**").hasRole("USER")

                        // ADMIN endpoints
                        .requestMatchers("/api/v1/admin/orders/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtValidator),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
