package com.nacer.reportes.dto;


import com.nacer.reportes.model.TipoRegistro;
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
public class RegistroDTO {

    private UUID id;
    private LocalDate fecha;
    private Float monto;
    private String detalle;
    private TipoRegistro tipoRegistro;
    private String efectorCuie;
    private String createdBy; // Email of the user who created the registro

}

