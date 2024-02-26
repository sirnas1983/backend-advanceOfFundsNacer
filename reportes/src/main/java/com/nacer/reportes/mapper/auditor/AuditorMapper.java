package com.nacer.reportes.mapper.auditor;

import com.nacer.reportes.dto.AuditorDTO;
import com.nacer.reportes.model.Auditor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuditorMapper {

    public AuditorDTO mapAuditorToAuditorDTO(Auditor auditor){
        AuditorDTO auditorDTO = new AuditorDTO();
        if(!Objects.isNull(auditor)){
            auditorDTO.setId(auditor.getId());
            auditorDTO.setFechaModificacion(auditor.getFechaDeModificacion());
            auditorDTO.setFechaCreacion(auditor.getFechaCreacion());
        }
        return auditorDTO;
    }

    public Auditor mapAuditorDTOToAuditor(AuditorDTO auditorDTO){
        Auditor auditor= new Auditor();
        if(!Objects.isNull(auditorDTO)){
            auditor.setId(auditorDTO.getId());
            auditor.setFechaDeModificacion(auditorDTO.getFechaModificacion());
            auditor.setFechaCreacion(auditorDTO.getFechaCreacion());
        }
        return auditor;
    }

}
