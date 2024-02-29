package com.nacer.reportes.service.expediente;

import com.nacer.reportes.dto.ExpedienteDTO;
import com.nacer.reportes.dto.ResolucionDTO;
import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Expediente;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpedienteService {

    void crearExpediente(@Valid ExpedienteDTO expediente);

    void actualizarExpediente(ExpedienteDTO exDto);

    Optional<ExpedienteDTO> getExpedientePorNumEx(String numEx);

    Collection<? extends ExpedienteDTO> getExpedientesPorEfector(String cuie);

    Collection<? extends ExpedienteDTO> getExpedientesPorRegion(String region);

    Collection<? extends ExpedienteDTO> getExpedientes();
}
