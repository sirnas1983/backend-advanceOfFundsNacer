package com.nacer.reportes.service.expediente;

import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Expediente;
import com.nacer.reportes.model.Resolucion;
import com.nacer.reportes.repository.expediente.ExpedienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpedienteServiceImpl implements ExpedienteService{

    @Autowired
    ExpedienteRepository expedienteRepository;

    @Override
    public Optional<Expediente> getExpedientePorNumero(String numero) {
        return expedienteRepository.findByNumero(numero);
    }

    @Override
    public Optional<Expediente> getExpedientePorId(UUID id) {
        return expedienteRepository.findById(id);
    }

    @Override
    public List<Expediente> getTodosLosExpedientes() {
        return expedienteRepository.findAll();
    }

    @Override
    public List<Expediente> getExpedientePorEfector(Efector efector) {
        return expedienteRepository.findByEfector(efector);
    }

    @Override
    public Optional<Expediente> getExpedientePorResolucion(Resolucion resolucion) {
        return expedienteRepository.findByResolucion(resolucion);
    }

    @Override
    public List<Expediente> getExpedientesSinResolucion() {
        return expedienteRepository.findSinResolucion();
    }

    @Override
    public void crearExpediente(Expediente expediente) {
        expedienteRepository.save(expediente);

    }
}
