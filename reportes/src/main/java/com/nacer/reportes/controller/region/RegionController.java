package com.nacer.reportes.controller.region;

import com.nacer.reportes.constants.ApiConstants;
import com.nacer.reportes.dto.RegionConEfectoresDTO;
import com.nacer.reportes.dto.ResponseWrapper;
import com.nacer.reportes.service.region.RegionService;
import com.nacer.reportes.service.registro.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = ApiConstants.BASE_URL + "/regiones")
public class RegionController {


    @Autowired
    private RegionService regionService;

    @Autowired
    private RegistroService registroService;

    @GetMapping
    public ResponseEntity<?> verRegiones(){
        List<RegionConEfectoresDTO> regiones = regionService.getRegiones();

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(),
                "Regiones encontradas",
                regiones));
    }

    @GetMapping(value = "/registros")
    public ResponseEntity<?> findAllSumaDebeSumaHaberByRegion(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(HttpStatus.OK.value(),
                        "Resumen regiones efectuado",
                        regionService.findAllSumaDebeSumaHaberByRegion())
        );
    }
}
