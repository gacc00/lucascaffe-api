package com.paolo.lucascaffe.security;

import com.paolo.lucascaffe.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "lucascaffe-secret-key-must-be-at-least-32-chars";
    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 horas

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, User user) {
        return extractEmail(token).equals(user.getEmail())
                && !isTokenExpired(token);
    }
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
