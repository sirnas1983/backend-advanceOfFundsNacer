package com.nacer.reportes.dto;

import com.nacer.reportes.model.Region;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private AuditorDTO auditorDTO;
    private Double totalHaber;
    private Double totalDebe;
    private Double saldo;

    // Method to calculate derived properties based on the fetched data
    public void calculateDerivedProperties(Double totalHaber, Double totalDebe) {
        this.totalHaber =   Math.round(totalHaber * 100) / 100.0;
        this.totalDebe = Math.round(totalDebe * 100) / 100.0;
        this.saldo = Math.round((this.totalHaber - this.totalDebe) * 100) / 100.0;
    }
}

