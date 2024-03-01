package com.nacer.reportes.controller.expediente;

import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.ExpedienteDTO;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.service.efector.EfectorService;
import com.nacer.reportes.service.expediente.ExpedienteService;
import io.jsonwebtoken.ExpiredJwtException;
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

@RestController
@RequestMapping(value = ApiConstants.BASE_URL + "/expedientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpedienteController {

    @Autowired
    ExpedienteService expedienteService;
    @Autowired
    EfectorService efectorService;

    @Secured("ADMIN")
    @PostMapping
    public ResponseEntity<?> persistExpediente(@RequestBody @Valid ExpedienteDTO expDto) {
        try {
            // Check if the associated Efector exists
            String cuie = expDto.getEfector().getCuie();
            if (!efectorService.existsByCuie(cuie)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: el efector con CUIE " + cuie + " no existe");
            }

            // If the Efector exists, proceed with creating the Expediente
            expedienteService.crearExpediente(expDto);
            return ResponseEntity.status(HttpStatus.OK).body("Expediente creado correctamente");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: revise que no exista expediente con ese Numero");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en creación de expediente");
        }
    }

    @Secured("ADMIN")
    @GetMapping
    public ResponseEntity<?> getExpedientesPorParametros(
            @RequestParam(name = "numEx", required = false) @Valid String numEx,
            @RequestParam(name = "cuie", required = false) @Valid String cuie,
            @RequestParam(name = "region", required = false) @Valid String region) {

        try{
            List<ExpedienteDTO> expedientes = new ArrayList<>();

            if (numEx != null) {
                // Search by numEx
                Optional<ExpedienteDTO> expedienteDTO = expedienteService.getExpedientePorNumEx(numEx);
                expedienteDTO.ifPresent(expedientes::add);
            } else if (cuie != null) {
                // Search by cuie
                expedientes.addAll(expedienteService.getExpedientesPorEfector(cuie));
            } else if (region != null) {
                // Search by region
                expedientes.addAll(expedienteService.getExpedientesPorRegion(region));
            } else {
                // If no parameter is provided, return all expedientes
                expedientes.addAll(expedienteService.getExpedientes());
            }

            // Check if the list is empty and return appropriate response
            if (expedientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(expedientes);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(expedientes);
            }
        } catch (
        ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
        }
    }
    @Secured("ADMIN")
    @PutMapping
    public ResponseEntity<?> actualizarExpediente(@RequestBody @Valid ExpedienteDTO exDto) {
        try {
            expedienteService.actualizarExpediente(exDto);
            return ResponseEntity.ok("Resolucion actualizada correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resolucion no encontrada con ID: " + exDto.getId());
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la resolucion");
        }
    }
}
