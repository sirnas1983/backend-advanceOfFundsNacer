package com.nacer.reportes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expediente extends EntidadNacer{

    @Column(unique = true)
    private String numero;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Efector efector;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;
    private Float montoSolicitado;
    private LocalDate fechaExpediente;

}
