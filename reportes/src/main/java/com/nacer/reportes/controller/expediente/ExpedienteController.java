package com.nacer.reportes.controller.expediente;

import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.ExpedienteDTO;
import com.nacer.reportes.dto.ResponseWrapper;
import com.nacer.reportes.exceptions.InvalidDataException;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.service.efector.EfectorService;
import com.nacer.reportes.service.expediente.ExpedienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = ApiConstants.BASE_URL + "/expedientes")
public class ExpedienteController {

    @Autowired
    ExpedienteService expedienteService;

    @Autowired
    EfectorService efectorService;

    @Secured("ADMIN")
    @PostMapping
    public ResponseEntity<?> persistExpediente(@RequestBody @Valid ExpedienteDTO expDto) {
        // Check if the associated Efector exists
        String cuie = expDto.getEfector().getCuie();
        if (!efectorService.existsByCuie(cuie)) {
            throw new InvalidDataException("El efector con CUIE " + cuie + " no existe");
        }

        // If the Efector exists, proceed with creating the Expediente
        expedienteService.crearExpediente(expDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(),
                        "Expediente creado correctamente",
                        null)
        );
    }

    @Secured("ADMIN")
    @GetMapping
    public ResponseEntity<?> getExpedientesPorParametros(
            @RequestParam(name = "numEx", required = false) @Valid String numEx,
            @RequestParam(name = "cuie", required = false) @Valid String cuie,
            @RequestParam(name = "region", required = false) @Valid String region) {
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
            throw new ResourceNotFoundException("No se encontraron expedientes");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseWrapper<>(HttpStatus.OK.value(),
                            "Expedientes encontrados",
                            expedientes)
            );
        }
    }

    @Secured("ADMIN")
    @PutMapping
    public ResponseEntity<?> actualizarExpediente(@RequestBody @Valid ExpedienteDTO exDto) {
        expedienteService.actualizarExpediente(exDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(),
                        "Expediente modificado correctamente",
                        null)
        );
    }
}
