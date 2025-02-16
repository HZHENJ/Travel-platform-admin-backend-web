package com.example.backendweb.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds (24 hours)

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private JwtParser getJwtParser() {
        return Jwts.parser().verifyWith(getSigningKey()).build();
    }

    /**
     * Generate JWT token for the given username
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), Jwts.SIG.HS256) // Use correct method for JJWT 0.12.x
                .compact();
    }

    /**
     * Validate JWT token
     */
    public boolean validateToken(String token) {
        try {
            getJwtParser().parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extract username from the JWT token
     */
    public String extractUsername(String token) {
        try {
            Claims claims = getJwtParser().parseSignedClaims(token).getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extract JWT token from the request header
     */
    public String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
