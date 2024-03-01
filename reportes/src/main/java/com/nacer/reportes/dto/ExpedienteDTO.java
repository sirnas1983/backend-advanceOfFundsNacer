package com.nacer.reportes.dto;

import com.nacer.reportes.model.Efector;
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
public class ExpedienteDTO {

    private UUID id;
    private String nombre;
    private String numero;
    private EfectorDTO efector;
    private Float montoSolicitado;
    private LocalDate fechaExpediente;
    private AuditorDTO auditorDTO;
}
