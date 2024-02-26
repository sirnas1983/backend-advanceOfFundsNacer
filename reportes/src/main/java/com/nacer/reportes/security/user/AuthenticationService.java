package com.nacer.reportes.security.user;

import com.nacer.reportes.security.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nacer.reportes.security.jwt.GenerateJwtToken;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private GenerateJwtToken generateJwtToken;
    public String authenticateUser(String email, String password) {
        try {
            // Load user details from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Create an authentication token
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Generate JWT token
            String jwtToken = generateJwtToken.generateJwtToken(authentication);

            // Return JWT token to client
            return jwtToken;

        } catch (UsernameNotFoundException ex) {
        // Handle the case where the user was not found
        throw new AuthenticationException("User not found with email: " + email);
    }
    }
}
