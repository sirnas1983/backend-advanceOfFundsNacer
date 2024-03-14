package com.nacer.reportes.service.registro;

import com.nacer.reportes.dto.RegistroDTO;
import com.nacer.reportes.exceptions.InvalidDataException;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.mapper.ObjectMapper;
import com.nacer.reportes.mapper.registro.RegistroMapper;
import com.nacer.reportes.model.Auditor;
import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Registro;
import com.nacer.reportes.model.TipoRegistro;
import com.nacer.reportes.repository.registro.RegistroRepository;
import com.nacer.reportes.service.efector.EfectorService;
import com.nacer.reportes.service.auth.AuthServiceImpl;
import com.nacer.reportes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private RegistroRepository registroRepository;
    @Autowired
    private RegistroMapper registroMapper;
    @Autowired
    private EfectorService efectorService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthServiceImpl authService;

    @Override
    public List<RegistroDTO> getTodosLosRegistros() {
        List<Registro> registros = registroRepository.findAll();
        return registroMapper.mapToListRegistrosDTO(registros);
    }

    @Override
    public List<RegistroDTO> getTodosLosRegistrosPorCuie(String cuie, String tipo) {
        List<Registro> registros = registroRepository.findByCuie(cuie);
        return registroMapper.mapToListRegistrosDTO(registros);
    }

    @Override
    public void crearRegistro(RegistroDTO registroDTO) {
        // Check if the efector exists
        String efectorCuie = registroDTO.getEfectorCuie();
        if (!efectorService.existsByCuie(efectorCuie)) {
            throw new ResourceNotFoundException("Efector not found with Cuie: " + efectorCuie);
        }

        // Validate tipoRegistro
        if (!isValidTipoRegistro(String.valueOf(registroDTO.getTipoRegistro()))) {
            throw new InvalidDataException("Invalid TipoRegistro: " + registroDTO.getTipoRegistro());
        }

        // Map RegistroDTO to Registro entity
        Registro registro = registroMapper.mapToRegistro(registroDTO);
        // Set the efector if available
        Efector efector = efectorService.getEfectorByCuie(efectorCuie)
                .orElseThrow(() -> new ResourceNotFoundException("Efector not found with Cuie: " + efectorCuie));
        registro.setEfector(efector);

        //Set Auditor to registro entity
        Auditor auditor = new Auditor(LocalDate.now(), LocalDate.now());
        auditor.setCreadoPor(authService.getCurrentUser());
        registro.setAuditor(auditor);
        // Save the registro entity
        registroRepository.save(registro);
        efectorService.actualizarSaldosEfector(efector, registroRepository.getTotalDebeByCuie(efector.getCuie()), registroRepository.getTotalHaberByCuie(efector.getCuie()));
    }

    @Override
    public void updateRegistro(RegistroDTO registroDTO) {
        // Check if the efector exists
        if (!efectorService.existsByCuie(registroDTO.getEfectorCuie())) {
            throw new ResourceNotFoundException("Efector not found with Cuie: " + registroDTO.getEfectorCuie());
        }

        // Validate tipoRegistro
        if (!isValidTipoRegistro(String.valueOf(registroDTO.getTipoRegistro()))) {
            throw new InvalidDataException("Invalid TipoRegistro: " + registroDTO.getTipoRegistro());
        }

        // Find the registro by ID or throw exception
        Registro registro = registroRepository.findById(registroDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Registro not found with ID: " + registroDTO.getId()));

        // Retrieve original auditor
        Auditor auditor = registro.getAuditor();

        // Update fields using ObjectMapper
        ObjectMapper.mapFields(registroDTO, registro);

        // Set the modified date and user to registro entity
        auditor.setFechaDeModificacion(LocalDate.now());
        auditor.setModificadoPor(authService.getCurrentUser());
        registro.setAuditor(auditor);

        // Save the updated Registro entity
        registroRepository.save(registro);
        efectorService.actualizarSaldosEfector(registro.getEfector(), registroRepository.getTotalDebeByCuie(registro.getEfector().getCuie()), registroRepository.getTotalHaberByCuie(registro.getEfector().getCuie()));

    }

    // Method to validate TipoRegistro enum
    private boolean isValidTipoRegistro(String tipoRegistro) {
        try {
            TipoRegistro.valueOf(tipoRegistro);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}