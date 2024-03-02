package com.nacer.reportes.exceptions;

public class ExpiredJwtAuthenticationException extends RuntimeException{

        public ExpiredJwtAuthenticationException(String message) {
            super(message);
        }

    }

