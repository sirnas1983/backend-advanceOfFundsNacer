package com.nacer.reportes.controller.user;

import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.ResponseWrapper;
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
    @Secured("ADMIN")
    @ResponseBody
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
    public ResponseEntity<ResponseWrapper<?>> getUsers(
            @RequestParam(name="id", required = false)
            @Valid UUID id) {
        if (Objects.isNull(id)) {
            List<UserDTO> userDTOs = userService.getUsers();
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Success", userDTOs));
        } else {
            Optional<UserDTO> userOptional = userService.getUser(id);
            return userOptional.<ResponseEntity<ResponseWrapper<?>>>map(userDTO ->
                    ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(),
                            "Success", userDTO))).orElseGet(
                                    () -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), "User not found", null)));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) throws AuthenticationException {
        // Authenticate user
        String jwtToken = authenticationService.authenticateUser(request.getEmail(), request.getPassword());
        // Return authentication token in the response body
        return ResponseEntity.ok(Map.of("token", jwtToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseWrapper<?>> logout(HttpServletRequest request) {
        String jwtToken = JwtUtils.extractTokenFromRequest(request);
        tokenBlacklistService.blacklistToken(jwtToken);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Logged out successfully", null));
    }

    @PutMapping
    @Secured("ADMIN")
    @ResponseBody
    public ResponseEntity<ResponseWrapper<?>> editUser(@RequestBody @Valid UserDTO userDTO) {
        // Call the service method to edit the user
        userService.editUser(userDTO);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Logged out successfully", null));

    }
}
