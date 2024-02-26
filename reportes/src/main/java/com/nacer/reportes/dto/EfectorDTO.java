package com.nacer.reportes.dto;

import com.nacer.reportes.model.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EfectorDTO {

    private UUID id;
    private String nombre;
    private String cuie;
    private Region region;
    private List<ExpedienteDTO> expedientes = new ArrayList<>();
    private List<RegistroDTO> registros = new ArrayList<>();;
    private Float partidaPersupestaria;
    private AuditorDTO auditorDTO;
}

