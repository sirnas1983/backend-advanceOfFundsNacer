package com.nacer.reportes.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.lang.Nullable;

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Nullable
    private User creadoPor;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Nullable
    private User modificadoPor;

}
