package com.nacer.reportes.security.user;

import com.nacer.reportes.model.User;
import com.nacer.reportes.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.nacer.reportes.security.jwt.GenerateJwtToken;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private GenerateJwtToken generateJwtToken;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUser(String email, String password) throws AuthenticationException {
        // Load user details by email
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Check if the provided password matches the encoded password in the user details
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            // If authentication succeeds, update last login timestamp
            User user = userRepository.findByEmail(email).get(); // Assuming UserDetails is implemented by your User class
            user.setLastLoginDate(LocalDateTime.now());
            userRepository.save(user); // Save the updated user details
            // Passwords match, generate JWT token
            return generateJwtToken.generateJwtToken(userDetails);
        } else {
            // Passwords do not match, throw authentication exception
            throw new AuthenticationException("Invalid email/password combination");
        }
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsService.loadUserByUsername(username);
    }
}
