package com.nacer.reportes.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class TokenBlacklistService {
    private Set<String> blacklistedTokens = new HashSet<>();

    public void cleanExpiredTokens() {
        Iterator<String> iterator = blacklistedTokens.iterator();
        while (iterator.hasNext()) {
            String token = iterator.next();
            if (isTokenExpired(token)) {
                iterator.remove();
            }
        }
    }

    // Check if a token is expired (
    private boolean isTokenExpired(String token) {
        try {
            // Parse the token
            Jws<Claims> claims = Jwts.parser().parseClaimsJws(token);

            // Get the expiration date from the claims
            Date expirationDate = claims.getBody().getExpiration();

            // Check if the expiration date is in the past
            return expirationDate.before(new Date());
        } catch (Exception e) {
            // Token parsing failed or expired date is missing
            return true;
        }
    }

    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}