package com.nacer.reportes.exceptions;

import com.nacer.reportes.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ExpiredJwtAuthenticationException.class, InvalidDataException.class})
    public ResponseEntity<?> handleCustomException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleOtherException(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null));
    }
}
