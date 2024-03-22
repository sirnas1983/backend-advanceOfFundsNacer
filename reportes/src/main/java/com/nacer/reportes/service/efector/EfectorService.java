package com.nacer.reportes.service.efector;

import com.nacer.reportes.dto.EfectorDTO;
import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Registro;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EfectorService {

    Optional<EfectorDTO> getEfectorDtoPorCuie(String cuie);

    void actualizarSaldosEfector(Efector efector, Double totalDebe, Double totalHaber);

    void crearEfector(EfectorDTO efectorDTO);

    Collection<? extends EfectorDTO> getEfectoresPorRegion(String region);

    Collection<? extends EfectorDTO> getTodosLosEfectores();

    void actualizarEfector(EfectorDTO efectorDTO);

    boolean existsByCuie(String cuie);

    Optional<Efector> getEfectorByCuie(String cuie);

    List<Efector> getAll();
}
