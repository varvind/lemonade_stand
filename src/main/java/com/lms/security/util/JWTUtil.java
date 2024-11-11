package com.lms.security.util;

import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTUtil {

    @Value("${server.jwt.key}")
    private String secretKey;  // use a more secure secret key

    // Generate a JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Validate the token
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract claims (e.g., expiration) from the token
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Get Authentication object from token (to be used in JwtAuthenticationFilter)
    public Authentication getAuthentication(String token) {
        String username = extractUsername(token);

        // Optionally, you can fetch the user's roles from a database or service here.
        // For simplicity, we assume the user has no roles assigned at this point.
        List<GrantedAuthority> authorities = new ArrayList<>(); // Add roles if needed

        User principal = new User(username, "", authorities);

        // Return an Authentication object
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
