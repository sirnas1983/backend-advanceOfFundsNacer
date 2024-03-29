package com.nacer.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditorDTO {

    private UUID id;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;
    private String creadoPor;
    private String modificadoPor;

}