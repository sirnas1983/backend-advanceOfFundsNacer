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
            if(!Objects.isNull(auditor.getCreadoPor())) {
                auditorDTO.setCreadoPor(auditor.getCreadoPor().getEmail());
            }else {
                auditorDTO.setCreadoPor("originDB");
            }
            if (!Objects.isNull(auditor.getModificadoPor())){
                auditorDTO.setModificadoPor(auditor.getModificadoPor().getEmail());
            } else {
                auditorDTO.setModificadoPor("originDB");
            }
        }
        return auditorDTO;
    }

}