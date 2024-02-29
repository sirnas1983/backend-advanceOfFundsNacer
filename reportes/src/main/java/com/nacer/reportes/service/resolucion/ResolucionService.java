package com.nacer.reportes.service.resolucion;

import com.nacer.reportes.dto.ResolucionDTO;
import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Expediente;
import com.nacer.reportes.model.Resolucion;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResolucionService {

    Optional<ResolucionDTO> getResolucionPorNumero(String numero);
    Optional<ResolucionDTO> getResolucionPorId(UUID id);
    void actualizarResolucion(ResolucionDTO resDto);
    List<ResolucionDTO> getTodasLasResoluciones();
    void crearResolucion(ResolucionDTO resDto);
    List<ResolucionDTO> getResolucionPorEfector(String cuie);
    List<ResolucionDTO> getResolucionPorRegion(String region);

    Optional<ResolucionDTO> getResolucionPorNumEx(String numEx);
}
