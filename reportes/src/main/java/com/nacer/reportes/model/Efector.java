package com.nacer.reportes.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Efector extends EntidadNacer {

    @Column(unique = true)
    private String cuie;
    @Enumerated(EnumType.STRING)
    private Region region;
    private String localidad;
    private String codRecupero;
    @OneToMany(mappedBy="efector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expediente> expedientes = new ArrayList<>();
    @OneToMany(mappedBy="efector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registro> registros = new ArrayList<>();
    @Formula("(select COALESCE(sum(" +
            "case when r.tipo_registro = 'DEBE' " +
            "then r.monto else 0 end), 0) " +
            "from Registro r " +
            "where r.efector = id)")
    private Double totalDebe;
    @Formula("(select COALESCE(sum(" +
            "case when r.tipo_registro = 'HABER' " +
            "then r.monto else 0 end), 0) " +
            "from Registro r " +
            "where r.efector = id)")
    private Double totalHaber;
    public Efector(String cuie, Region region) {
        this.cuie = cuie;
        this.region = region;
    }

}
