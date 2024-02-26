package com.nacer.reportes.service.resolucion;

import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Expediente;
import com.nacer.reportes.model.Resolucion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResolucionService {

    Optional<Resolucion> getResolucionPorNumero(String numero);
    Optional<Resolucion> getResolucionPorId(UUID id);
    List<Resolucion> getTodasLasResoluciones();
    List<Resolucion> getResolucionPorEfector(Efector efector);
    Optional<Resolucion> getResolucionPorExpediente(Expediente resolucion);
    void crearResolucion(Resolucion resolucion);
}
