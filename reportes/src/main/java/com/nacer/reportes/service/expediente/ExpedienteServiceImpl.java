package com.nacer.reportes.service.expediente;

import com.nacer.reportes.dto.ExpedienteDTO;
import com.nacer.reportes.dto.RegistroDTO;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.mapper.expediente.ExpedienteMapper;
import com.nacer.reportes.model.*;
import com.nacer.reportes.repository.expediente.ExpedienteRepository;
import com.nacer.reportes.repository.registro.RegistroRepository;
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
    private RegistroRepository registroRepository;
    @Autowired
    private ExpedienteMapper expedienteMapper;
    @Autowired
    private EfectorService efectorService;
    @Autowired
    private AuthServiceImpl authService;

    @Override
    public void crearExpediente(ExpedienteDTO expedienteDto) {


        String cuie = expedienteDto.getEfectorDTO().getCuie();
        Efector efector = efectorService.getEfectorByCuie(cuie)
                .orElseThrow(() -> new ResourceNotFoundException("Efector not found with CUIE: " + cuie));

        Expediente expediente = expedienteMapper.mapToExpediente(expedienteDto);

        expediente.setEfector(efector);

        Auditor auditor = new Auditor(LocalDate.now(),LocalDate.now());
        auditor.setCreadoPor(authService.getCurrentUser());
        auditor.setModificadoPor(authService.getCurrentUser());

        expediente.setAuditor(auditor);

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

        expedienteRepository.save(expediente);
        efectorService.actualizarSaldosEfector(efector, registroRepository.getTotalDebeByCuie(efector.getCuie()), registroRepository.getTotalHaberByCuie(efector.getCuie()));

    }

    @Override
    public void actualizarExpediente(ExpedienteDTO exDto) {
        // Check if the resource exists
        Expediente existingExpediente = expedienteRepository.findById(exDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Expediente not found with id: " + exDto.getId()));

        // Retrieve the original Auditor
        Auditor auditor = existingExpediente.getAuditor();

        // Retrieve the Efector from the database based on the EfectorDTO in the ExpedienteDTO
        Efector efector = efectorService.getEfectorByCuie(exDto.getEfectorDTO().getCuie())
                .orElseThrow(() -> new ResourceNotFoundException("Efector not found with Cuie: " + exDto.getEfectorDTO().getCuie()));

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

        existingExpediente = expedienteMapper.mergeToExpediente(exDto, existingExpediente);

        auditor.setModificadoPor(authService.getCurrentUser());
        auditor.setFechaDeModificacion(LocalDate.now());
        existingExpediente.setAuditor(auditor);

        existingExpediente.setRegistro(registro);

        existingExpediente.setEfector(efector);

        expedienteRepository.save(existingExpediente);
        efectorService.actualizarSaldosEfector(efector, registroRepository.getTotalDebeByCuie(efector.getCuie()), registroRepository.getTotalHaberByCuie(efector.getCuie()));

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

    @Override
    public void eliminarExpediente(ExpedienteDTO expediente) {
        expedienteRepository.deleteById(expediente.getId());
        Efector efector = efectorService.getEfectorByCuie(expediente.getEfectorDTO().getCuie()).orElseThrow(
                ()-> new ResourceNotFoundException("No se encontro efector con CUIE:" + expediente.getEfectorDTO().getCuie()));
        efectorService.actualizarSaldosEfector(efector, registroRepository.getTotalDebeByCuie(efector.getCuie()), registroRepository.getTotalHaberByCuie(efector.getCuie()));
    }
}
