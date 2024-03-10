package com.nacer.reportes.service.expediente;

import com.nacer.reportes.dto.ExpedienteDTO;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.mapper.expediente.ExpedienteMapper;
import com.nacer.reportes.model.*;
import com.nacer.reportes.repository.expediente.ExpedienteRepository;
import com.nacer.reportes.service.efector.EfectorService;
import com.nacer.reportes.service.auth.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ExpedienteServiceImpl implements ExpedienteService{

    @Autowired
    private ExpedienteRepository expedienteRepository;
    @Autowired
    private ExpedienteMapper expedienteMapper;
    @Autowired
    private EfectorService efectorService;
    @Autowired
    private AuthServiceImpl authService;

    @Override
    public void crearExpediente(ExpedienteDTO expedienteDto) {

        // Retrieve the Efector data from the DTO
        String cuie = expedienteDto.getEfector().getCuie();
        Efector efector = efectorService.getEfectorByCuie(cuie)
                .orElseThrow(() -> new ResourceNotFoundException("Efector not found with CUIE: " + cuie));

        // Map ExpedienteDTO to Expediente entity
        Expediente expediente = expedienteMapper.mapToExpediente(expedienteDto);
        // Set the associated Efector
        expediente.setEfector(efector);

        Auditor auditor = new Auditor(LocalDate.now(),LocalDate.now());
        auditor.setCreadoPor(authService.getCurrentUser());
        auditor.setModificadoPor(authService.getCurrentUser());
        expediente.setAuditor(auditor);
        // Create the registro
        Registro registro = new Registro();
        registro.setDetalle("EXPDTE: " + expedienteDto.getNumero());
        registro.setTipoRegistro(TipoRegistro.DEBE);
        Double montoEx = expedienteDto.getMontoSolicitado();
        if (efector.getRegion().equals(Region.SUBSECRETARIA)){
            registro.setMonto(montoEx);
        } else {
            registro.setMonto(montoEx * 0.60);
        }
        registro.setEfector(efector);
        registro.setAuditor(auditor);
        registro.setFecha(expedienteDto.getFechaExpediente());
        expediente.setRegistro(registro);
        // Save the expediente
        expedienteRepository.save(expediente);
    }

    @Override
    public void actualizarExpediente(ExpedienteDTO exDto) {
        // Check if the resource exists
        Expediente existingExpediente = expedienteRepository.findById(exDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Expediente not found with id: " + exDto.getId()));

        // Retrieve the original Auditor
        Auditor auditor = existingExpediente.getAuditor();

        // Retrieve the Efector from the database based on the EfectorDTO in the ExpedienteDTO
        Efector efector = efectorService.getEfectorByCuie(exDto.getEfector().getCuie())
                .orElseThrow(() -> new ResourceNotFoundException("Efector not found with Cuie: " + exDto.getEfector().getCuie()));

        // Retrieve registro from existingExpediente
        Registro registro = existingExpediente.getRegistro();

        // Modify registro if changes detected in monto
        if (!Objects.equals(exDto.getMontoSolicitado(), existingExpediente.getMontoSolicitado())) {
            double monto = efector.getRegion().equals(Region.SUBSECRETARIA) ?
                    exDto.getMontoSolicitado() : exDto.getMontoSolicitado() * 0.60;
            monto = Math.round(monto * 100) /100.0;
            registro.setMonto(monto);
            registro.getAuditor().setFechaDeModificacion(LocalDate.now());
            registro.getAuditor().setModificadoPor(authService.getCurrentUser());
        }

        // Map fields from DTO to existing Expediente
        existingExpediente = expedienteMapper.mergeToExpediente(exDto, existingExpediente);

        // Check if the Auditor is not null before updating modification date
        if (auditor != null) {
            // Update modification date of the auditor
            auditor.setFechaDeModificacion(LocalDate.now());
        }

        // Set registro to expediente
        existingExpediente.setRegistro(registro);

        // Set the Efector in the Expediente
        existingExpediente.setEfector(efector);

        // Set the updated Auditor back to the Expediente
        existingExpediente.setAuditor(auditor);

        // Save the updated Expediente entity
        expedienteRepository.save(existingExpediente);
    }


    @Override
    public Optional<ExpedienteDTO> getExpedientePorNumEx(String numEx) {
        return Optional.of(
                expedienteMapper.mapToExpedienteDTO(
                        expedienteRepository.findByNumero(numEx).isPresent() ?
                        expedienteRepository.findByNumero(numEx).get() : null)
        );
    }

    @Override
    public List<ExpedienteDTO> getExpedientesPorEfector(String cuie) {
        return expedienteMapper.mapToListExpedienteDTO(expedienteRepository.findByCuie(cuie));
    }

    @Override
    public List<ExpedienteDTO> getExpedientesPorRegion(String region) {
        try {
            Region regionEnum = Region.valueOf(region);
            return expedienteMapper.mapToListExpedienteDTO(
                    expedienteRepository.findByRegion(regionEnum));
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("No se encontró Region " + region);
        }
    }

    @Override
    public List<ExpedienteDTO> getExpedientes() {
        return expedienteMapper.mapToListExpedienteDTO(expedienteRepository.findAll());
    }
}
