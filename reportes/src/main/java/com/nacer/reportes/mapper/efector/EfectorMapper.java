package com.nacer.reportes.mapper.efector;

import com.nacer.reportes.dto.EfectorDTO;
import com.nacer.reportes.mapper.ListMapper;
import com.nacer.reportes.mapper.auditor.AuditorMapper;
import com.nacer.reportes.mapper.expediente.ExpedienteMapper;
import com.nacer.reportes.mapper.registro.RegistroMapper;
import com.nacer.reportes.model.Efector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class EfectorMapper {

    @Autowired
    private AuditorMapper auditorMapper;

    @Autowired
    private RegistroMapper registroMapper;

    @Autowired
    private ExpedienteMapper expedienteMapper;

    public EfectorDTO mapToEfectorDTO(Efector efector){
        EfectorDTO efectorDTO = new EfectorDTO();
        if (!Objects.isNull(efector)){
            efectorDTO.setId(efector.getId());
            efectorDTO.setCuie(efector.getCuie());
            efectorDTO.setNombre(efector.getNombre());
            efectorDTO.setExpedientes(expedienteMapper.mapToListExpedienteDTO(efector.getExpedientes())); //TODO: pasar expedientes a ExpedientesDTO
            efectorDTO.setRegion(efector.getRegion());
            efectorDTO.setRegistros(registroMapper.mapToListRegistrosDTO(efector.getRegistros())); //TODO: pasar registros
            efectorDTO.setPartidaPersupestaria(efector.getPartidaPersupestaria());
            efectorDTO.setAuditorDTO(auditorMapper.mapToAuditorDTO(efector.getAuditor()));
        }
        return efectorDTO;
    }

    public Efector mapToEfector(EfectorDTO efectorDTO){
        Efector efector = new Efector();
        if (!Objects.isNull(efectorDTO)) {
            efector.setId(efectorDTO.getId());
            efector.setCuie(efectorDTO.getCuie());
            efector.setNombre(efectorDTO.getNombre());
            efector.setRegion(efectorDTO.getRegion());
            efector.setPartidaPersupestaria(efectorDTO.getPartidaPersupestaria());
        }
        return efector;
    }

    public List<Efector> mapToListEfector(List<EfectorDTO> listaEfectorDTO) {
        return ListMapper.mapListaToLista(listaEfectorDTO, this::mapToEfector);
    }

    public List<EfectorDTO> mapToListEfectorDTO(List<Efector> listaEfector) {
        return ListMapper.mapListaToLista(listaEfector, this::mapToEfectorDTO);
    }

}