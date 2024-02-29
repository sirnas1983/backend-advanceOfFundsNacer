package com.nacer.reportes.mapper.efector;

import com.nacer.reportes.dto.EfectorDTOSimplificado;
import com.nacer.reportes.mapper.auditor.AuditorMapper;
import com.nacer.reportes.model.Efector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EfectorSimplificadoMapper {

    @Autowired
    private AuditorMapper auditorMapper;

    public EfectorDTOSimplificado mapEfectorToEfectorDTOSimplificado(Efector efector) {
        EfectorDTOSimplificado efectorDTOSimplificado = new EfectorDTOSimplificado();
        if (efector != null) {
            efectorDTOSimplificado.setId(efector.getId());
            efectorDTOSimplificado.setNombre(efector.getNombre());
            efectorDTOSimplificado.setCuie(efector.getCuie());
            efectorDTOSimplificado.setRegion(efector.getRegion());
            efectorDTOSimplificado.setPartidaPersupestaria(efector.getPartidaPersupestaria());
            efectorDTOSimplificado.setAuditorDTO(auditorMapper.mapToAuditorDTO(efector.getAuditor()));
        }
        return efectorDTOSimplificado;
    }

    public Efector mapToEfector(EfectorDTOSimplificado efectorDTOSimplificado) {
        Efector efector = new Efector();
        if (efectorDTOSimplificado != null) {
            efector.setId(efectorDTOSimplificado.getId());
            efector.setNombre(efectorDTOSimplificado.getNombre());
            efector.setCuie(efectorDTOSimplificado.getCuie());
            efector.setRegion(efectorDTOSimplificado.getRegion());
            efector.setPartidaPersupestaria(efectorDTOSimplificado.getPartidaPersupestaria());
            efector.setAuditor(auditorMapper.mapToAuditor(efectorDTOSimplificado.getAuditorDTO()));
        }
        return efector;
    }

}
