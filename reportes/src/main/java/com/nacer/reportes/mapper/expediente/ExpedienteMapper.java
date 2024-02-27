package com.nacer.reportes.mapper.expediente;

import com.nacer.reportes.dto.ExpedienteDTO;
import com.nacer.reportes.mapper.ListMapper;
import com.nacer.reportes.mapper.auditor.AuditorMapper;
import com.nacer.reportes.mapper.efector.EfectorMapper;
import com.nacer.reportes.mapper.efector.EfectorSimplificadoMapper;
import com.nacer.reportes.model.Expediente;
import com.nacer.reportes.service.efector.EfectorService;
import com.nacer.reportes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ExpedienteMapper {

    @Autowired
    AuditorMapper auditorMapper;
    @Autowired
    UserService userService;

    @Autowired
    EfectorSimplificadoMapper efectorSimplificadoMapper;
    @Autowired
    EfectorService efectorService;

    public ExpedienteDTO mapExpedienteToExpedienteDTO(Expediente expediente){

        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        if (!Objects.isNull(expediente)) {
            expedienteDTO.setFechaExpediente(expediente.getFechaExpediente());
            expedienteDTO.setId(expediente.getId());
            expedienteDTO.setEfectorDTOSimplificado(efectorSimplificadoMapper.mapEfectorToEfectorDTOSimplificado(expediente.getEfector()));
            expedienteDTO.setNombre(expediente.getNombre());
            expedienteDTO.setNumero(expediente.getNumero());
            expedienteDTO.setUserEmail(expediente.getUser().getEmail());
            expedienteDTO.setMontoSolicitado(expediente.getMontoSolicitado());
            expedienteDTO.setAuditorDTO(auditorMapper.mapAuditorToAuditorDTO(expediente.getAuditor())); //TODO: Map auditor AuditorDTO
        }
        return expedienteDTO;
    }

    public Expediente mapExpedienteDTOToExpediente(ExpedienteDTO expedienteDTO){
        Expediente expediente = new Expediente();
        if (!Objects.isNull(expedienteDTO)) {
            expediente.setFechaExpediente(expedienteDTO.getFechaExpediente());
            expediente.setId(expedienteDTO.getId());
            expediente.setEfector(efectorService.getEfectorPorCuie(expedienteDTO.getEfectorDTOSimplificado().getCuie()).orElseThrow()); // TODO: Map UserEfector
            expediente.setNombre(expedienteDTO.getNombre());
            expediente.setNumero(expedienteDTO.getNumero());
            expediente.setUser(userService.getUserByEmail(expedienteDTO.getUserEmail()).orElseThrow());
            expediente.setMontoSolicitado(expedienteDTO.getMontoSolicitado());
            expediente.setAuditor(auditorMapper.mapAuditorDTOToAuditor(expedienteDTO.getAuditorDTO()));
        }
        return expediente;
    }

    public List<Expediente> mapListaExpedienteDTOToListaExpediente(List<ExpedienteDTO> listaExpedienteDTO) {
        return ListMapper.mapListaToLista(listaExpedienteDTO, this::mapExpedienteDTOToExpediente);
    }

    public List<ExpedienteDTO> mapListaExpedienteToListaExpedienteDTO(List<Expediente> listaExpediente) {
        return ListMapper.mapListaToLista(listaExpediente, this::mapExpedienteToExpedienteDTO);
    }

}
