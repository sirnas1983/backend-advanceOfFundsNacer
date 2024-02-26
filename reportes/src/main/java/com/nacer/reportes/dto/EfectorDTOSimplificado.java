package com.nacer.reportes.dto;

import com.nacer.reportes.model.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EfectorDTOSimplificado {

    private UUID id;
    private String nombre;
    private String cuie;
    private Region region;
    private Float partidaPersupestaria;
    private AuditorDTO auditorDTO;
}
