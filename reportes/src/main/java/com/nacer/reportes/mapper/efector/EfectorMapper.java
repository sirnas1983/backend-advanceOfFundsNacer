package com.nacer.reportes.mapper.efector;

import com.nacer.reportes.dto.EfectorDTO;
import com.nacer.reportes.dto.EfectorDTOSimplificado;
import com.nacer.reportes.mapper.ListMapper;
import com.nacer.reportes.mapper.auditor.AuditorMapper;
import com.nacer.reportes.mapper.expediente.ExpedienteMapper;
import com.nacer.reportes.mapper.registro.RegistroMapper;
import com.nacer.reportes.model.Efector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EfectorMapper {

    @Autowired
    AuditorMapper auditorMapper;
    @Autowired
    RegistroMapper registroMapper;
    @Autowired
    ExpedienteMapper expedienteMapper;


    public EfectorDTO mapEfectorToEfectorDTO(Efector efector){
        EfectorDTO efectorDTO = new EfectorDTO();
        if (!Objects.isNull(efector)){
            efectorDTO.setId(efector.getId());
            efectorDTO.setCuie(efector.getCuie());
            efectorDTO.setNombre(efector.getNombre());
            efectorDTO.setExpedientes(expedienteMapper.mapListaExpedienteToListaExpedienteDTO(efector.getExpedientes())); //TODO: pasar expedientes a ExpedientesDTO
            efectorDTO.setRegion(efector.getRegion());
            efectorDTO.setRegistros(registroMapper.mapListaRegistrosToListaRegistrosDTO(efector.getRegistros())); //TODO: pasar registros
            efectorDTO.setPartidaPersupestaria(efector.getPartidaPersupestaria());
            efectorDTO.setAuditorDTO(auditorMapper.mapAuditorToAuditorDTO(efector.getAuditor()));
        }
        return efectorDTO;
    }

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

    public Efector mapEfectorDtoToEfector(EfectorDTO efectorDTO){
        Efector efector = new Efector();
        if (!Objects.isNull(efectorDTO)) {
            efector.setId(efectorDTO.getId());
            efector.setCuie(efectorDTO.getCuie());
            efector.setNombre(efectorDTO.getNombre());
            efector.setExpedientes(expedienteMapper.mapListaExpedienteDTOToListaExpediente(efectorDTO.getExpedientes())); //TODO: pasar expedientes a ExpedientesDTO
            efector.setRegion(efectorDTO.getRegion());
            efector.setRegistros(registroMapper.mapListaRegistrosDtoToListaRegistros(efectorDTO.getRegistros())); //TODO: pasar registros
            efector.setPartidaPersupestaria(efectorDTO.getPartidaPersupestaria());
            efector.setAuditor(auditorMapper.mapAuditorDTOToAuditor(efectorDTO.getAuditorDTO()));
        }
        return efector;
    }

    public List<Efector> mapListaExpedienteDTOToListaExpediente(List<EfectorDTO> listaEfectorDTO) {
        return ListMapper.mapListaToLista(listaEfectorDTO, this::mapEfectorDtoToEfector);
    }

    public List<EfectorDTO> mapListaExpedienteToListaExpedienteDTO(List<Efector> listaEfector) {
        return ListMapper.mapListaToLista(listaEfector, this::mapEfectorToEfectorDTO);
    }

}
