package com.nacer.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String username; // Assuming username is the email
    private String email;
    private List<String> roles;
    private boolean validated;
    private boolean unlocked;
    private LocalDateTime lastLoginDate;
}