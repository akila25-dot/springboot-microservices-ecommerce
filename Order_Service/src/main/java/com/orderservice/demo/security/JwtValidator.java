package com.orderservice.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;

@Component
public class JwtValidator {

    private final Key key;

    public JwtValidator(JwtProperties props) {
        byte[] decodedKey = Base64.getDecoder().decode(props.secret());
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    public Claims validate(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
