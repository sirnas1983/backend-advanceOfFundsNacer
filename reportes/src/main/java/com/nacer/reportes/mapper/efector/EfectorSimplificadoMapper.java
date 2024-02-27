package com.nacer.reportes.mapper.efector;

import com.nacer.reportes.dto.EfectorDTOSimplificado;
import com.nacer.reportes.mapper.auditor.AuditorMapper;
import com.nacer.reportes.model.Efector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EfectorSimplificadoMapper {

    @Autowired
    AuditorMapper auditorMapper;

    public EfectorDTOSimplificado mapEfectorToEfectorDTOSimplificado(Efector efector) {
        EfectorDTOSimplificado efectorDTOSimplificado = new EfectorDTOSimplificado();
        if (efector != null) {
            efectorDTOSimplificado.setId(efector.getId());
            efectorDTOSimplificado.setNombre(efector.getNombre());
            efectorDTOSimplificado.setCuie(efector.getCuie());
            efectorDTOSimplificado.setRegion(efector.getRegion());
            efectorDTOSimplificado.setPartidaPersupestaria(efector.getPartidaPersupestaria());
            efectorDTOSimplificado.setAuditorDTO(auditorMapper.mapAuditorToAuditorDTO(efector.getAuditor()));
        }
        return efectorDTOSimplificado;
    }
}
