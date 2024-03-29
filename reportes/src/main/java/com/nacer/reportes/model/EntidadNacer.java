package com.nacer.reportes.model;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;



@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class EntidadNacer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;
    @Column(unique = true)
    protected String nombre;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Auditor auditor;
    private String descripcion;

}