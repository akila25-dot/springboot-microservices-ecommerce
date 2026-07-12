package com.orderservice.demo.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtValidator jwtValidator;

    public JwtAuthenticationFilter(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                Claims claims = jwtValidator.validate(token);
                String username = claims.getSubject();
                String role = claims.get("role", String.class);

                var auth = new UsernamePasswordAuthenticationToken(
                        username,
                        token, // ✅ raw JWT stored here
                        List.of(new SimpleGrantedAuthority(role != null ? role : "ROLE_USER"))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
