package com.nacer.reportes.service.region;

import com.nacer.reportes.dto.RegionConEfectoresDTO;
import com.nacer.reportes.dto.others.ResumenRegionDTO;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.mapper.region.RegionMapper;
import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Region;
import com.nacer.reportes.model.RegionEnum;
import com.nacer.reportes.repository.efector.EfectorRepository;
import com.nacer.reportes.repository.region.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {


    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private EfectorRepository efectorRepository;

    @Override
    public Optional<Region> getRegionPorNombre(String nombre) {
        RegionEnum regionEnum;
        try {
            regionEnum = RegionEnum.valueOf(nombre);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("No se encuentra Region " + nombre);
        }

        Region region = regionRepository.findByRegionEnum(regionEnum);
        if (region == null) {
            return Optional.empty(); // Devolver Optional vacío si no se encontró la región
        } else {
            return Optional.of(region); // Envolver la región encontrada en un Optional
        }
    }

    @Override
    public List<RegionConEfectoresDTO> getRegiones() {
        List<RegionConEfectoresDTO> regionConEfectoresDTOS = new ArrayList<>();
        List<Efector> efectores =  efectorRepository.findAll();

        List<Region> regiones = regionRepository.findAll();
        for(Region region : regiones){
            RegionConEfectoresDTO regionConEfectoresDTO = regionMapper.mapToDtoConEfector(region);

            regionConEfectoresDTO.setEfectores(
             efectores.stream().filter(efector -> efector.getRegion().equals(region))
                     .map(efector -> regionMapper.mapToEfectorSimpleDTO(efector))
                    .collect(Collectors.toList())
             );


            regionConEfectoresDTOS.add(regionConEfectoresDTO);

        }



        return regionConEfectoresDTOS;
    }

    @Override
    public List<ResumenRegionDTO> findAllSumaDebeSumaHaberByRegion() {
        List<Object[]> resultados = regionRepository.findAllSumaDebeSumaHaberByRegion();
        List<ResumenRegionDTO> resumenRegionDTOS = new ArrayList<>();
        for (Object[] resultado : resultados) {
            String nombreRegion = (String) resultado[0];
            Double sumaDebe = (Double) resultado[1];
            Double sumaHaber = (Double) resultado[2];
            resumenRegionDTOS.add(new ResumenRegionDTO(nombreRegion, sumaDebe, sumaHaber));
        }
        return resumenRegionDTOS;
    }

}
