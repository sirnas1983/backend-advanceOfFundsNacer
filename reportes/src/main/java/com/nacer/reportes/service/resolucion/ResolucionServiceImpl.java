package com.nacer.reportes.service.resolucion;

import com.nacer.reportes.dto.ResolucionDTO;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.mapper.ObjectMapper;
import com.nacer.reportes.mapper.resolucion.ResolucionMapper;
import com.nacer.reportes.model.Auditor;
import com.nacer.reportes.model.Expediente;
import com.nacer.reportes.model.Region;
import com.nacer.reportes.model.Resolucion;
import com.nacer.reportes.repository.expediente.ExpedienteRepository;
import com.nacer.reportes.repository.resolucion.ResolucionRepository;
import com.nacer.reportes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResolucionServiceImpl implements ResolucionService{

    @Autowired
    private ResolucionRepository resolucionRepository;

    @Autowired
    private ResolucionMapper resolucionMapper;

    @Autowired
    private ExpedienteRepository expedienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Override
    public Optional<ResolucionDTO> getResolucionPorNumero(String numero) {
        Resolucion resolucion = resolucionRepository.findByNumero(numero);
        return Optional.ofNullable(resolucion)
                .map(resolucionMapper::mapToResolucionDTO);
    }

    @Override
    public Optional<ResolucionDTO> getResolucionPorId(UUID id) {
        return resolucionRepository.findById(id)
                .map(resolucionMapper::mapToResolucionDTO);
    }

    @Override
    public void actualizarResolucion(ResolucionDTO resDto) {
        // Check if the resource exists
        Resolucion existingResolucion = resolucionRepository.findById(resDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resolucion not found with id: " + resDto.getId()));

        // Retrieve the original Auditor
        Auditor auditor = existingResolucion.getAuditor();

        // Map fields from DTO to existing Resolucion
        ObjectMapper.mapFields(resDto, existingResolucion);

        // Update modification date of the auditor
        auditor.setFechaDeModificacion(LocalDate.now());

        // Set the updated Auditor back to the Resolucion
        existingResolucion.setAuditor(auditor);

        // Save the updated Resolucion entity
        resolucionRepository.save(existingResolucion);
    }

    @Override
    public List<ResolucionDTO> getTodasLasResoluciones() {
        return resolucionMapper.mapToListResolucionDTO(
                resolucionRepository.findAll());
    }

    @Override
    public void crearResolucion(ResolucionDTO resDto) {
        // Retrieve the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Expediente exp = expedienteRepository.findByNumero(
                resDto.getExpedienteDTO().getNumero()).orElseThrow(() -> new ResourceNotFoundException("Expediente not found with number: " + resDto.getExpedienteDTO().getNumero()));

        Resolucion resolucion = resolucionMapper.mapToResolucion(resDto);
        // Set the authenticated user to the expediente
        resolucion.setUser(userService.getUserByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail)));

        resolucion.setAuditor(new Auditor(LocalDate.now(), LocalDate.now()));
        resolucion.setExpediente(exp);
        resolucionRepository.save(resolucion);
    }

    @Override
    public List<ResolucionDTO> getResolucionPorEfector(String cuie) {
        return resolucionMapper.mapToListResolucionDTO(
                resolucionRepository.findByCuie(cuie));
    }

    @Override
    public List<ResolucionDTO> getResolucionPorRegion(String region) {
        try {
            Region regionEnum = Region.valueOf(region);

            return resolucionMapper.mapToListResolucionDTO(
                    resolucionRepository.findByRegion(regionEnum));
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("No se encontró Region " + region);
        }
    }

    @Override
    public Optional<ResolucionDTO> getResolucionPorNumEx(String numEx) {
        return resolucionRepository.findByNumeroExpediente(numEx)
                .map(resolucionMapper::mapToResolucionDTO);
    }
}