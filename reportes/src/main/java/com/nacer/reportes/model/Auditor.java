package com.nacer.reportes.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auditor {

    public Auditor(LocalDate fechaCreacion, LocalDate fechaDeModificacion){
        this.fechaCreacion = fechaCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate fechaCreacion;
    private LocalDate fechaDeModificacion;

}
