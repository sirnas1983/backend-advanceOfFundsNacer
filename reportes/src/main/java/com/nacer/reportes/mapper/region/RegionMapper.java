package com.nacer.reportes.mapper.region;

import com.nacer.reportes.dto.EfectorDTO;
import com.nacer.reportes.dto.RegionConEfectoresDTO;
import com.nacer.reportes.dto.RegionDTO;
import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Region;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RegionMapper {

    public RegionDTO mapToDto(Region region){
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setRegionNombre(region.getRegionEnum().toString());
        regionDTO.setSaldoInicial(region.getSaldoInicial());
        regionDTO.setSaldo(region.getSaldo());

        return  regionDTO;
    }

    public RegionConEfectoresDTO mapToDtoConEfector(Region region){
        RegionConEfectoresDTO regionConEfectoresDTO = new RegionConEfectoresDTO();
        regionConEfectoresDTO.setRegionNombre(region.getRegionEnum().toString());
        regionConEfectoresDTO.setSaldoInicial(region.getSaldoInicial());
        regionConEfectoresDTO.setSaldo(region.getSaldo());

        return  regionConEfectoresDTO;
    }

    public EfectorDTO mapToEfectorSimpleDTO(Efector efector){
        EfectorDTO efectorDTO = new EfectorDTO();
        if (!Objects.isNull(efector)){
            efectorDTO.setId(efector.getId());
            efectorDTO.setNombre(efector.getNombre());
            efectorDTO.setCuie(efector.getCuie());
            efectorDTO.setRegion(efector.getRegion().getNombre());
            efectorDTO.setDescripcion(efector.getDescripcion());
            Double totalDebe = (efector.getTotalDebe() != null) ? efector.getTotalDebe() : 0.0;
            Double totalHaber = (efector.getTotalHaber() != null) ? efector.getTotalHaber() : 0.0;

            efectorDTO.setTotalDebe(totalDebe);
            efectorDTO.setTotalHaber(totalHaber);
            efectorDTO.setSaldo(totalHaber - totalDebe);
        }
        return efectorDTO;
    }

}
