package com.nacer.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EfectorSimplificadoDTO {
    private UUID id;
    private String nombre;
    private String cuie;
    private Double totalHaber;
    private Double totalDebe;
    private Double saldo;
    private String descripcion;

}
