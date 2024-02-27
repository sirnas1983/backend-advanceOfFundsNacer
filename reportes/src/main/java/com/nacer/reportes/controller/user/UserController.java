package com.nacer.reportes.controller.user;

import com.nacer.reportes.model.AuthenticationRequest;
import com.nacer.reportes.model.Rol;
import com.nacer.reportes.model.User;
import com.nacer.reportes.security.user.AuthenticationService;
import com.nacer.reportes.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {

        user.addRole(Rol.USER);
        userService.addUser(user);

        return ResponseEntity.status(HttpStatus.OK).body("User successfully created");
    }

    @GetMapping("/all")
    @Secured("USER")
    @ResponseBody
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            // Authenticate user
            String jwtToken = authenticationService.authenticateUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(jwtToken);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // Extract JWT token from the request
            return ResponseEntity.ok("Logged out successfully");
    }
}
