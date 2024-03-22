package com.nacer.reportes.dto.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumenRegionDTO {
    private String nombreRegion;
    private Double sumaDebe;
    private Double sumaHaber;
}
