package com.nacer.reportes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate fecha;
    private Float monto;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn (name = "efector")
    private Efector efector;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Auditor auditor;
    @Enumerated(EnumType.STRING)
    private TipoRegistro tipoRegistro;
    private String detalle;


}
