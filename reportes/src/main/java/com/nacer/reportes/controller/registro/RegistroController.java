package com.nacer.reportes.controller.registro;

import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.RegistroDTO;
import com.nacer.reportes.dto.ResponseWrapper;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.service.registro.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> persistRegistro(@RequestBody @Valid RegistroDTO registroDTO) {
        registroService.crearRegistro(registroDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(),
                        "Registro creado correctamente",
                        null)
        );
    }

    @Secured("ADMIN")
    @GetMapping
    public ResponseEntity<?> getRegistros(
            @RequestParam(name = "cuie", required = false) @Valid String cuie,
            @RequestParam(name = "tipo", required = false) @Valid String tipo) {
        List<RegistroDTO> registros;
        if (Objects.isNull(cuie) && Objects.isNull(tipo)) {
            registros = registroService.getTodosLosRegistros();
        } else {
            registros = registroService.getTodosLosRegistrosPorCuie(cuie, tipo);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(),
                        "Registros encontrados",
                        registros)
        );
    }

    @Secured("ADMIN")
    @PutMapping
    public ResponseEntity<?> updateRegistro(@RequestBody @Valid RegistroDTO registroDTO) {
        registroService.updateRegistro(registroDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(),
                        "Registro actualizado correctamente",
                        null)
        );
    }
}

