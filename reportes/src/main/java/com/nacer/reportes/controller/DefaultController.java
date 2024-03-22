package com.nacer.reportes.controller;

import com.nacer.reportes.constants.ApiConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})

@RestController
public class DefaultController {

    @GetMapping
    public ResponseEntity<?> defaultApi(){
        return ResponseEntity.status(HttpStatus.OK)
                .body("Bienvenido a Plan Sumar");
    }
}
