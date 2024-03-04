package com.nacer.reportes.mapper.expediente;

import com.nacer.reportes.dto.AuditorDTO;
import com.nacer.reportes.dto.EfectorDTO;
import com.nacer.reportes.dto.ExpedienteDTO;
import com.nacer.reportes.mapper.ListMapper;
import com.nacer.reportes.mapper.ObjectMapper;
import com.nacer.reportes.mapper.auditor.AuditorMapper;
import com.nacer.reportes.mapper.efector.EfectorMapper;
import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Expediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ExpedienteMapper {

    @Autowired
    private AuditorMapper auditorMapper;
    public ExpedienteDTO mapToExpedienteDTO(Expediente expediente){
        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        if (!Objects.isNull(expediente)) {
            expedienteDTO.setFechaExpediente(expediente.getFechaExpediente());
            expedienteDTO.setId(expediente.getId());
            expedienteDTO.setNombre(expediente.getNombre());
            EfectorDTO efectorDTO = new EfectorDTO();
            Efector efector = expediente.getEfector();
            ObjectMapper.mapFields(expediente.getEfector(), efectorDTO);
            efectorDTO.setNombre(expediente.getEfector().getNombre());
            expedienteDTO.setEfector(efectorDTO);
            expedienteDTO.setNumero(expediente.getNumero());
            expedienteDTO.setMontoSolicitado(expediente.getMontoSolicitado());
            expedienteDTO.setAuditorDTO(auditorMapper.mapToAuditorDTO(expediente.getAuditor()));
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
