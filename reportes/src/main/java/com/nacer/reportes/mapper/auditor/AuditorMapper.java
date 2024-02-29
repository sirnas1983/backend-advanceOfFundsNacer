package com.nacer.reportes.mapper.auditor;

import com.nacer.reportes.dto.AuditorDTO;
import com.nacer.reportes.model.Auditor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuditorMapper {

    public AuditorDTO mapToAuditorDTO(Auditor auditor){
        AuditorDTO auditorDTO = new AuditorDTO();
        if(!Objects.isNull(auditor)){
            auditorDTO.setId(auditor.getId());
            auditorDTO.setFechaModificacion(auditor.getFechaDeModificacion());
            auditorDTO.setFechaCreacion(auditor.getFechaCreacion());
        }
        return auditorDTO;
    }

    public Auditor mapToAuditor(AuditorDTO auditorDTO){
        Auditor auditor= new Auditor();
        if(!Objects.isNull(auditorDTO)){
            auditor.setId(auditorDTO.getId());
            auditor.setFechaDeModificacion(auditorDTO.getFechaModificacion());
            auditor.setFechaCreacion(auditorDTO.getFechaCreacion());
        }
        return auditor;
    }
}