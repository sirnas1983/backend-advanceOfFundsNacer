package com.nacer.reportes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Efector extends EntidadNacer{

    @Column(unique = true)
    private String cuie;
    @Enumerated(EnumType.STRING)
    private Region region;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Expediente> expedientes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Registro> registros = new ArrayList<>();;
    @Column(name="partida")
    private Float partidaPersupestaria;

    public Efector(String cuie, Region region) {
        this.cuie = cuie;
        this.region = region;
    }

}
