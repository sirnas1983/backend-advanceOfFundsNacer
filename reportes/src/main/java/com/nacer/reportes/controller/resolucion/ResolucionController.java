package com.nacer.reportes.controller.resolucion;


import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.ResolucionDTO;
import com.nacer.reportes.exceptions.ExpiredJwtAuthenticationException;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.service.resolucion.ResolucionService;
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
@RequestMapping(value = ApiConstants.BASE_URL + "/resoluciones", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ResolucionController {

    @Autowired
    ResolucionService resolucionService;
    @Secured("ADMIN")
    @PostMapping
    public ResponseEntity<?> persistResolucion(@RequestBody @Valid ResolucionDTO resDto){
        try {
            resolucionService.crearResolucion(resDto);
            return ResponseEntity.status(HttpStatus.OK).body("Recurso creado correctamente");
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: revise que no exista resolucion con ese Numero");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en generacion de recurso");
        }
    }

    @GetMapping
    public ResponseEntity<?> getResolucionesByParams(
            @RequestParam(name = "numRe", required = false) String numRe,
            @RequestParam(name = "numEx", required = false) String numEx,
            @RequestParam(name = "cuie", required = false) String cuie,
            @RequestParam(name = "region", required = false) String region) {
        try {
            List<ResolucionDTO> resoluciones = new ArrayList<>();
            if (numRe != null) {
                // Search by numeroResolucion
                Optional<ResolucionDTO> resolucionDTO = resolucionService.getResolucionPorNumero(numRe);
                resolucionDTO.ifPresent(resoluciones::add);
            } else if (numEx != null) {
                // Search by numEx
                Optional<ResolucionDTO> resolucionDTO = resolucionService.getResolucionPorNumEx(numEx);
                resolucionDTO.ifPresent(resoluciones::add);
            } else if (cuie != null) {
                // Search by cuie
                resoluciones.addAll(resolucionService.getResolucionPorEfector(cuie));
            } else if (region != null) {
                // Search by region
                resoluciones.addAll(resolucionService.getResolucionPorRegion(region));
            } else {
                // If no parameter is provided, return all resoluciones
                resoluciones.addAll(resolucionService.getTodasLasResoluciones());
            }
            // Check if the list is empty and return appropriate response
            if (resoluciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resoluciones);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(resoluciones);
            }
        } catch (ExpiredJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
        }
    }
    @Secured("ADMIN")
    @PutMapping
    public ResponseEntity<?> actualizarResolucion(@RequestBody @Valid ResolucionDTO resDto) {
        try {
            resolucionService.actualizarResolucion(resDto);
            return ResponseEntity.ok("Resolucion actualizada correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ExpiredJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la resolucion");
        }
    }
}
