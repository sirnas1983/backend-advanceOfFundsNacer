package com.nacer.reportes.service.region;

import com.nacer.reportes.dto.RegionConEfectoresDTO;
import com.nacer.reportes.dto.others.ResumenRegionDTO;
import com.nacer.reportes.model.Region;

import java.util.List;
import java.util.Optional;


public interface RegionService {

    Optional<Region> getRegionPorNombre(String nombre);

    List<RegionConEfectoresDTO> getRegiones();

    List<ResumenRegionDTO> findAllSumaDebeSumaHaberByRegion();
}
