package com.nacer.reportes.controller.efector;

import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.EfectorDTO;
import com.nacer.reportes.dto.ResponseWrapper;
import com.nacer.reportes.exceptions.ExpiredJwtAuthenticationException;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.service.efector.EfectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = ApiConstants.BASE_URL + "/efectores")
public class EfectorController {

    @Autowired
    EfectorService efectorService;

    @Secured("ADMIN")
    @PostMapping
    public ResponseEntity<?> persistEfector(@RequestBody @Valid EfectorDTO efectorDTO) {
        efectorService.crearEfector(efectorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(),
                        "Efector creado correctamente",
                        null)
        );    }

    @Secured("ADMIN")
    @PutMapping
    public ResponseEntity<?> actualizarEfector(@RequestBody @Valid EfectorDTO efectorDTO) {
        efectorService.actualizarEfector(efectorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(),
                        "Efector modificado correctamente",
                        null)
                );
    }

    @GetMapping
    public ResponseEntity<?> getEfectoresPorParametros(
            @RequestParam(name = "cuie", required = false) String cuie,
            @RequestParam(name = "region", required = false) String region) {

        List<EfectorDTO> efectores = new ArrayList<>();

        if (cuie != null) {
            // Search by cuie
            Optional<EfectorDTO> efectorDTO = efectorService.getEfectorDtoPorCuie(cuie);
            efectorDTO.ifPresent(efectores::add);
        } else if (region != null) {
            // Search by region
            efectores.addAll(efectorService.getEfectoresPorRegion(region));
        } else {
            // If no parameter is provided, return all efectores
            efectores.addAll(efectorService.getTodosLosEfectores());
        }

        // Check if the list is empty and return appropriate response
        if (efectores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(efectores);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(efectores);
        }
    }
}
