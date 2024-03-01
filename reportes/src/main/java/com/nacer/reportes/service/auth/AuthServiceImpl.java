package com.nacer.reportes.service.auth;

import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.model.User;
import com.nacer.reportes.security.user.UserDetailsImpl;
import com.nacer.reportes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserService userService;
    @Override
    public User getCurrentUser() {
        // Retrieve the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl) {
                String userEmail = ((UserDetailsImpl) principal).getEmail();
                return userService.getUserByEmail(userEmail).orElseThrow(
                        () -> new ResourceNotFoundException("No user found with email:" + userEmail)
                );
            }
        }
        // If no user is authenticated or the principal is not an instance of User, return null or throw an exception as per your requirement
        throw new AuthenticationCredentialsNotFoundException("No credentials found for this user");
    }
}
