package com.nacer.reportes.controller.user;

import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.UserDTO;
import com.nacer.reportes.dto.AuthenticationUserDto;
import com.nacer.reportes.exceptions.ExpiredJwtAuthenticationException;
import com.nacer.reportes.model.AuthenticationRequest;
import com.nacer.reportes.security.jwt.JwtUtils;
import com.nacer.reportes.security.jwt.TokenBlacklistService;
import com.nacer.reportes.security.user.AuthenticationService;
import com.nacer.reportes.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.*;

@RestController
@RequestMapping(value = ApiConstants.BASE_URL + "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    TokenBlacklistService tokenBlacklistService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid AuthenticationUserDto userDTO) {
        // Check if a user with the provided email already exists
        if (userService.existsByEmail(userDTO.getEmail())) {
            // Return a response indicating that the email is already in use
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is already in use");
        }
        // If the email doesn't exist, proceed to add the new user
        userService.addUser(userDTO);

        // Return a success response
        return ResponseEntity.status(HttpStatus.OK)
                .body("User successfully created");
    }

    @GetMapping
    @Secured("ADMIN")
    @ResponseBody
    public ResponseEntity<?> getUsers(
            @RequestParam(name="id", required = false)
            @Valid UUID id) {
        try {
            if (Objects.isNull(id)) {
                // If no ID is provided, return all users
                List<UserDTO> userDTOs = userService.getUsers();
                return ResponseEntity.ok(userDTOs);
            } else {
                // Find the user by ID
                Optional<UserDTO> userOptional = userService.getUser(id);
                if (userOptional.isPresent()) {
                    // If the user is found, return it in the response body
                    return ResponseEntity.ok(userOptional.get());
                } else {
                    // If the user is not found, return a 404 Not Found response with an error message
                    String errorMessage = "User with ID " + id + " not found";
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
                }
            }
        } catch (ExpiredJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            // Authenticate user
            String jwtToken = authenticationService.authenticateUser(request.getEmail(), request.getPassword());
            // Return authentication token in the response body
            return ResponseEntity.ok(Map.of("token", jwtToken));
        } catch (AuthenticationException e) {
            // Return a detailed error response with an explanatory message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
            // Extract JWT token from the request
            try {
                String jwtToken = JwtUtils.extractTokenFromRequest(request);
                tokenBlacklistService.blacklistToken(jwtToken);
            } catch (ExpiredJwtAuthenticationException e) {
                return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
            } catch (Exception e) {
                // Return error response
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error processing logout");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Logged out successfully");
    }
}
