package com.nacer.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResolucionDTO {

    private UUID id;
    private String nombre;
    private String numero;
    private ExpedienteDTO expedienteDTO;
    private Float montoOtorgado;
    private LocalDate fechaResolucion;
    private AuditorDTO auditorDTO;
    private String descripcion;
    private boolean isFondosRendidos;


}
