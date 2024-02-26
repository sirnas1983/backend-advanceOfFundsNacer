package com.nacer.reportes.service.efector;

import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Region;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EfectorService {

    Optional<Efector> getEfectorPorCuie(String cuie);
    List<Efector> getEfectorPorNombre(String nombre);
    List<Efector> getEfectorPorRegion(Region region);
    Optional<Efector> getEfectorPorId(UUID id);
    void guardarEfector(Efector efector);

}
