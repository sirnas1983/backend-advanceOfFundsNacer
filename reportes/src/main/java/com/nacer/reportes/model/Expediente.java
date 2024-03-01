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
    @OneToOne(mappedBy = "expediente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Registro registro;
    private Float montoSolicitado;
    private LocalDate fechaExpediente;

}
