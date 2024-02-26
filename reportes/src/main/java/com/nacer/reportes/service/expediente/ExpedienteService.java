package com.nacer.reportes.service.expediente;

import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Expediente;
import com.nacer.reportes.model.Resolucion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpedienteService {


    Optional<Expediente> getExpedientePorNumero(String numero);
    Optional<Expediente> getExpedientePorId(UUID id);
    List<Expediente> getTodosLosExpedientes();
    List<Expediente> getExpedientePorEfector(Efector efector);
    Optional<Expediente> getExpedientePorResolucion(Resolucion resolucion);
    List<Expediente> getExpedientesSinResolucion();
    void crearExpediente(Expediente expediente);
}
