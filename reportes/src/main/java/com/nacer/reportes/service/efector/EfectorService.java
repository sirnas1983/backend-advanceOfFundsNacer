package com.nacer.reportes.service.efector;

import com.nacer.reportes.dto.EfectorDTO;
import com.nacer.reportes.model.Efector;

import java.util.Collection;
import java.util.Optional;

public interface EfectorService {

    Optional<EfectorDTO> getEfectorDtoPorCuie(String cuie);

    void crearEfector(EfectorDTO efectorDTO);

    Collection<? extends EfectorDTO> getEfectoresPorRegion(String region);

    Collection<? extends EfectorDTO> getTodosLosEfectores();

    void actualizarEfector(EfectorDTO efectorDTO);

    boolean existsByCuie(String cuie);

    Optional<Efector> getEfectorByCuie(String cuie);
}
