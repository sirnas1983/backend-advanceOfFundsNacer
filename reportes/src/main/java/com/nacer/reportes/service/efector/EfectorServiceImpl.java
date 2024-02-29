package com.nacer.reportes.service.efector;

import com.nacer.reportes.dto.EfectorDTO;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.mapper.ObjectMapper;
import com.nacer.reportes.model.*;
import com.nacer.reportes.mapper.efector.EfectorMapper;
import com.nacer.reportes.repository.efector.EfectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EfectorServiceImpl implements EfectorService{

    @Autowired
    private EfectorRepository efectorRepository;
    @Autowired
    private EfectorMapper efectorMapper;

    @Override
    public Optional<EfectorDTO> getEfectorDtoPorCuie(String cuie) {
        return efectorRepository.findByCuie(cuie)
                .map(efectorMapper::mapToEfectorDTO);
    }

    @Override
    public void crearEfector(EfectorDTO efectorDTO) {
        Efector efector = efectorMapper.mapToEfector(efectorDTO);
        efector.setId(null);
        efector.setExpedientes(new ArrayList<>());
        efector.setRegistros(new ArrayList<>());

        try {
            efector.setRegion(efectorDTO.getRegion());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid region: " + efectorDTO.getRegion().toString());
        }
        efector.setAuditor(new Auditor(LocalDate.now(),LocalDate.now()));
        efectorRepository.save(efector);
    }

    @Override
    public List<EfectorDTO> getEfectoresPorRegion(String region) {
        try {
            Region regionEnum = Region.valueOf(region);
            List<Efector> efectores = efectorRepository.findByRegion(regionEnum);
            return efectorMapper.mapToListEfectorDTO(efectores);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("No se encontró Region " + region);
        }
    }

    @Override
    public Collection<? extends EfectorDTO> getTodosLosEfectores() {
        List<Efector> efectores = efectorRepository.findAll();
        return efectorMapper.mapToListEfectorDTO(efectores);
    }

    @Override
    public void actualizarEfector(EfectorDTO efectorDTO) {
        // Retrieve the existing Efector from the repository
        Efector existingEfector = efectorRepository.findById(efectorDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Efector not found with ID: " + efectorDTO.getId()));

        // Retrieve the original unmutable values
        List<Registro> originalRegistros = existingEfector.getRegistros();
        List<Expediente> originalExpedientes = existingEfector.getExpedientes();
        Auditor originalAuditor = existingEfector.getAuditor();

        // Map fields from EfectorDTO to existing Efector entity
        ObjectMapper.mapFields(efectorDTO, existingEfector);

        // Set the original values
        existingEfector.setRegistros(originalRegistros);
        existingEfector.setExpedientes(originalExpedientes);
        existingEfector.setAuditor(originalAuditor);

        // Update modification date of the auditor
        existingEfector.getAuditor().setFechaDeModificacion(LocalDate.now());

        // Save the updated Efector entity back to the repository
        efectorRepository.save(existingEfector);
    }

    @Override
    public boolean existsByCuie(String cuie) {
        return efectorRepository.findByCuie(cuie).isPresent();
    }

    @Override
    public Optional<Efector> getEfectorByCuie(String cuie) {
        return efectorRepository.findByCuie(cuie);
    }
}
