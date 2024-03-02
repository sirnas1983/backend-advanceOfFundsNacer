package com.nacer.reportes.controller.registro;

import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.RegistroDTO;
import com.nacer.reportes.exceptions.ExpiredJwtAuthenticationException;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.service.registro.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping(value = ApiConstants.BASE_URL + "/registros", produces = {"application/json"})
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @Secured("ADMIN")
    @PostMapping
    public ResponseEntity<?> persistRegistro(
            @RequestBody @Valid RegistroDTO registroDTO) {
        try {
            registroService.crearRegistro(registroDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Registro creado correctamente");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: revise que no exista registro con ese ID");
        } catch (ExpiredJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en creación de registro");
        }
    }

    @Secured("ADMIN")
    @GetMapping
    public ResponseEntity<?> getRegistros(
            @RequestParam(name = "cuie", required = false) @Valid String cuie,
            @RequestParam(name = "tipo", required = false) @Valid String tipo) {
        try{
            List<RegistroDTO> registros;
            if (Objects.isNull(cuie) && Objects.isNull(tipo)) {
                registros = registroService.getTodosLosRegistros();
            } else {
                registros = registroService.getTodosLosRegistrosPorCuie(cuie, tipo);
            }
            if (registros.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(registros);
            }
        } catch (ExpiredJwtAuthenticationException e){
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
        }
    }

    @Secured("ADMIN")
    @PutMapping
    public ResponseEntity<?> updateRegistro(@RequestBody @Valid RegistroDTO registroDTO) {
        try {
            registroService.updateRegistro(registroDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Registro actualizado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro not found with ID: " + registroDTO.getId());
        } catch (ExpiredJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body("Sesion vencida. Inicie sesion nuevamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en actualización de registro");
        }
    }
}
