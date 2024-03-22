package com.nacer.reportes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "region")
public class Region extends EntidadNacer {

    @Enumerated(EnumType.STRING)
    @Column(unique=true)
    private RegionEnum regionEnum;
    private Double saldoInicial;
    private Double saldo;

}
