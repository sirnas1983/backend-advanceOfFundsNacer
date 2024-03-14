package com.nacer.reportes.controller.resolucion;

import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.ResolucionDTO;
import com.nacer.reportes.dto.ResponseWrapper;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.service.resolucion.ResolucionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        resolucionService.crearResolucion(resDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(), "Recurso creado correctamente", null)
        );
    }

    @GetMapping
    public ResponseEntity<?> getResolucionesByParams(
            @RequestParam(name = "numRe", required = false) String numRe,
            @RequestParam(name = "numEx", required = false) String numEx,
            @RequestParam(name = "cuie", required = false) String cuie,
            @RequestParam(name = "region", required = false) String region) {
        List<ResolucionDTO> resoluciones = new ArrayList<>();

        if (numRe != null) {
            // Search by numeroResolucion
            Optional<ResolucionDTO> resolucionDTO = resolucionService.getResolucionPorNumero(numRe);
            resoluciones.add(resolucionDTO.orElse(null));
        } else if (numEx != null) {
            // Search by numEx
            Optional<ResolucionDTO> resolucionDTO = resolucionService.getResolucionPorNumEx(numEx);
            resoluciones.add(resolucionDTO.orElse(null));
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
        return ResponseEntity.status(HttpStatus.OK).body(resoluciones);
    }

    @Secured("ADMIN")
    @PutMapping
    public ResponseEntity<?> actualizarResolucion(@RequestBody @Valid ResolucionDTO resDto) {
        resolucionService.actualizarResolucion(resDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(), "Resolucion actualizada correctamente", null)
        );
    }

    @Secured("ADMIN")
    @DeleteMapping
    public ResponseEntity<?> borrarResolucion(@RequestBody @Valid ResolucionDTO resDto) {
        resolucionService.eliminarResolucion(resDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(), "Resolucion borrada correctamente", null)
        );
    }
}
