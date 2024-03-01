package com.nacer.reportes.security.jwt;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Autowired
    TokenBlacklistService tokenBlacklistService;


    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            if (tokenBlacklistService.isTokenBlacklisted(authToken)) {
                logger.error("JWT token is blacklisted");
                return false;
            }
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public static String extractTokenFromRequest(HttpServletRequest request) {
        // Extract the Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer"
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token from the header
            return authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        // If Authorization header is not present or doesn't start with "Bearer", return null
        return null;
    }
}
