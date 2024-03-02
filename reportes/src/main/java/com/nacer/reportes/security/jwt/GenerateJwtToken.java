package com.nacer.reportes.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

public class GenerateJwtToken {

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    @Autowired
    private SecretKey key;

    public String generateJwtToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String roles = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // Include roles in the JWT claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }
}
