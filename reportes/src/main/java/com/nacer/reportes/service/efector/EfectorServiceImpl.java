package com.nacer.reportes.service.efector;

import com.nacer.reportes.dto.EfectorDTO;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.mapper.ObjectMapper;
import com.nacer.reportes.model.*;
import com.nacer.reportes.mapper.efector.EfectorMapper;
import com.nacer.reportes.repository.efector.EfectorRepository;
import com.nacer.reportes.repository.region.RegionRepository;
import com.nacer.reportes.service.auth.AuthServiceImpl;
import com.nacer.reportes.service.region.RegionService;
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

    @Autowired
    private RegionService regionService;

    @Autowired
    private AuthServiceImpl authService;

    @Override
    public Optional<EfectorDTO> getEfectorDtoPorCuie(String cuie) {
        return efectorRepository.findByCuie(cuie)
                .map(efectorMapper::mapToEfectorCompletoDTO);
    }

    @Override
    public void actualizarSaldosEfector(Efector efector, Double totalDebe, Double totalHaber) {

        // Actualizar los totales en el efector
        efector.setTotalDebe(totalDebe);
        efector.setTotalHaber(totalHaber);

        // Guardar el efector actualizado en la base de datos
        efectorRepository.save(efector);
    }

    @Override
    public void crearEfector(EfectorDTO efectorDTO) {
        Efector efector = efectorMapper.mapToEfector(efectorDTO);
        efector.setId(null);
        efector.setExpedientes(new ArrayList<>());
        efector.setRegistros(new ArrayList<>());

           efector.setRegion(regionService.getRegionPorNombre(efectorDTO.getRegion().toString()).orElseThrow(
                    ()-> new ResourceNotFoundException("No existe region: "+ efectorDTO.getRegion().toString()))
            );

        Auditor auditor = new Auditor(LocalDate.now(),LocalDate.now());
        auditor.setCreadoPor(authService.getCurrentUser());
        auditor.setModificadoPor(authService.getCurrentUser());
        efector.setAuditor(auditor);
        efectorRepository.save(efector);
    }

    @Override
    public List<EfectorDTO> getEfectoresPorRegion(String reg) {
        Region region = regionService.getRegionPorNombre(reg).orElseThrow(() -> new ResourceNotFoundException("No se encontro region: "+ reg));
        List<Efector> efectores = efectorRepository.findByRegion(region);
        return efectorMapper.mapToListEfectorDTO(efectores);
    }

    @Override
    public Collection<? extends EfectorDTO> getTodosLosEfectores() {
        List<Efector> efectores = efectorRepository.findAllWithRegion();
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

        // Update modification date of the auditor
        originalAuditor.setFechaDeModificacion(LocalDate.now());
        originalAuditor.setModificadoPor(authService.getCurrentUser());
        existingEfector.setAuditor(originalAuditor);

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

    @Override
    public List<Efector> getAll() {
        return efectorRepository.findAll();
    }
}
