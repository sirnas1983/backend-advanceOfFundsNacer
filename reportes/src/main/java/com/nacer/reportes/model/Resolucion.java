package com.nacer.reportes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "resolucion")
public class Resolucion extends EntidadNacer{

    @Column(unique = true)
    private String numero;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Expediente expediente;
    private Float montoOtorgado;
    private LocalDate fechaResolucion;
    @Column(columnDefinition = "boolean default false")
    private boolean isFondosRendidos;


}
