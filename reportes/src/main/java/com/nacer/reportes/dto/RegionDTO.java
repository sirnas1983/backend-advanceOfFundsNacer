package com.nacer.reportes.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegionDTO {

    private String regionNombre;
    private Double saldoInicial;
    private Double saldo;

}