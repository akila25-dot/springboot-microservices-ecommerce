/*package com.authservice.demo.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        // Generate a secure random key for HS256
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Encode as Base64 so you can paste into application.yml
        String base64Secret = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated JWT Secret: " + base64Secret);
    }
}
*/