package com.nacer.reportes.service.expediente;

import com.nacer.reportes.dto.ExpedienteDTO;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.mapper.ObjectMapper;
import com.nacer.reportes.mapper.efector.EfectorSimplificadoMapper;
import com.nacer.reportes.mapper.expediente.ExpedienteMapper;
import com.nacer.reportes.model.*;
import com.nacer.reportes.repository.expediente.ExpedienteRepository;
import com.nacer.reportes.service.efector.EfectorService;
import com.nacer.reportes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private EfectorSimplificadoMapper efectorSimplificadoMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private EfectorService efectorService;

    @Override
    public void crearExpediente(ExpedienteDTO expedienteDto) {
        // Retrieve the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication != null ? authentication.getName() : null;

        // Retrieve the Efector data from the DTO
        String cuie = expedienteDto.getEfectorDTOSimplificado().getCuie();
        Efector efector = efectorService.getEfectorByCuie(cuie)
                .orElseThrow(() -> new ResourceNotFoundException("Efector not found with CUIE: " + cuie));

        // Map ExpedienteDTO to Expediente entity
        Expediente expediente = expedienteMapper.mapToExpediente(expedienteDto);

        // Set the associated Efector
        expediente.setEfector(efector);

        if (userEmail != null) {
            // Set the authenticated user to the expediente
            expediente.setUser(userService.getUserByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail)));
        }

        expediente.setAuditor(new Auditor(LocalDate.now(), LocalDate.now()));

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
        Efector efector = efectorService.getEfectorByCuie(exDto.getEfectorDTOSimplificado().getCuie())
                .orElseThrow(() -> new ResourceNotFoundException("Efector not found with Cuie: " + exDto.getEfectorDTOSimplificado().getCuie()));

        // Map fields from DTO to existing Expediente
        ObjectMapper.mapFields(exDto, existingExpediente);

        // Check if the Auditor is not null before updating modification date
        if (auditor != null) {
            // Update modification date of the auditor
            auditor.setFechaDeModificacion(LocalDate.now());
        }

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
