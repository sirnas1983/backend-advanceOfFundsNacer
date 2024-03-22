package com.nacer.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class RegionConEfectoresDTO {

        private String regionNombre;
        private Double saldoInicial;
        private Double saldo;
        private List<EfectorDTO> efectores;

}