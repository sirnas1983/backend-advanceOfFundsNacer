package com.nacer.reportes.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.nacer.reportes.security.jwt.GenerateJwtToken;

@Service
public class AuthenticationService {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private GenerateJwtToken generateJwtToken;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUser(String email, String password) {
        // Load user details by email
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Check if the provided password matches the encoded password in the user details
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            // Passwords match, generate JWT token
            return generateJwtToken.generateJwtToken(userDetails);
        } else {
            // Passwords do not match, throw authentication exception
            throw new RuntimeException("Invalid email/password combination");
        }
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsService.loadUserByUsername(username);
    }
}
