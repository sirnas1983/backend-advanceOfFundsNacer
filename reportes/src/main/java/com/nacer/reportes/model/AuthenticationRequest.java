package com.nacer.reportes.model;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
