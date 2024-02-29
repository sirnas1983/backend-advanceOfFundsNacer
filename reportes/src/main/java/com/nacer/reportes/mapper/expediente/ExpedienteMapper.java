package com.nacer.reportes.mapper.expediente;

import com.nacer.reportes.dto.ExpedienteDTO;
import com.nacer.reportes.mapper.ListMapper;
import com.nacer.reportes.mapper.auditor.AuditorMapper;
import com.nacer.reportes.mapper.efector.EfectorSimplificadoMapper;
import com.nacer.reportes.model.Expediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ExpedienteMapper {

    @Autowired
    private AuditorMapper auditorMapper;

    @Autowired
    private EfectorSimplificadoMapper efectorSimplificadoMapper;

    public ExpedienteDTO mapToExpedienteDTO(Expediente expediente){
        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        if (!Objects.isNull(expediente)) {
            expedienteDTO.setFechaExpediente(expediente.getFechaExpediente());
            expedienteDTO.setId(expediente.getId());
            expedienteDTO.setEfectorDTOSimplificado(efectorSimplificadoMapper.mapEfectorToEfectorDTOSimplificado(expediente.getEfector()));
            expedienteDTO.setNombre(expediente.getNombre());
            expedienteDTO.setNumero(expediente.getNumero());
            expedienteDTO.setUserEmail(expediente.getUser().getEmail());
            expedienteDTO.setMontoSolicitado(expediente.getMontoSolicitado());
            expedienteDTO.setAuditorDTO(auditorMapper.mapToAuditorDTO(expediente.getAuditor())); //TODO: Map auditor AuditorDTO
        }
        return expedienteDTO;
    }

    public Expediente mapToExpediente(ExpedienteDTO expedienteDTO){
        Expediente expediente = new Expediente();
        if (!Objects.isNull(expedienteDTO)) {
            expediente.setFechaExpediente(expedienteDTO.getFechaExpediente());
            expediente.setId(expedienteDTO.getId());
            expediente.setNombre(expedienteDTO.getNombre());
            expediente.setNumero(expedienteDTO.getNumero());
            expediente.setMontoSolicitado(expedienteDTO.getMontoSolicitado());
        }
        return expediente;
    }



    public List<Expediente> mapToListExpediente(List<ExpedienteDTO> listaExpedienteDTO) {
        return ListMapper.mapListaToLista(listaExpedienteDTO, this::mapToExpediente);
    }

    public List<ExpedienteDTO> mapToListExpedienteDTO(List<Expediente> listaExpediente) {
        return ListMapper.mapListaToLista(listaExpediente, this::mapToExpedienteDTO);
    }
}
