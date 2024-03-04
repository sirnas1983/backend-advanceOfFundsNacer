package com.nacer.reportes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Expediente extends EntidadNacer{

    @Column(unique = true)
    private String numero;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "efector")
    private Efector efector;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Registro registro;
    private Double montoSolicitado;
    private LocalDate fechaExpediente;

}
