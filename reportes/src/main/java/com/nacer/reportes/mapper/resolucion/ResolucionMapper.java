package com.nacer.reportes.mapper.resolucion;

import com.nacer.reportes.dto.ResolucionDTO;
import com.nacer.reportes.mapper.ListMapper;
import com.nacer.reportes.mapper.auditor.AuditorMapper;
import com.nacer.reportes.mapper.expediente.ExpedienteMapper;
import com.nacer.reportes.model.Expediente;
import com.nacer.reportes.model.Resolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;

@Component
public class ResolucionMapper {

    @Autowired
    private ExpedienteMapper expedienteMapper;
    @Autowired
    private AuditorMapper auditorMapper;

    public ResolucionDTO mapToResolucionDTO(Resolucion resolucion) {
        ResolucionDTO resolucionDTO = new ResolucionDTO();
        if (!Objects.isNull(resolucion)) {
            resolucionDTO.setId(resolucion.getId());
            resolucionDTO.setNombre(resolucion.getNombre());
            resolucionDTO.setNumero(resolucion.getNumero());
            resolucionDTO.setExpedienteDTO(expedienteMapper.mapToExpedienteDTO(resolucion.getExpediente()));
            resolucionDTO.setMontoOtorgado(resolucion.getMontoOtorgado());
            resolucionDTO.setFechaResolucion(resolucion.getFechaResolucion());
            resolucionDTO.setDescripcion(resolucion.getDescripcion());
            resolucionDTO.setFondosRendidos(resolucion.isFondosRendidos());
            resolucionDTO.setAuditorDTO(auditorMapper.mapToAuditorDTO(resolucion.getAuditor()));

        }
        return resolucionDTO;
    }

    public Resolucion mapToResolucion(ResolucionDTO resolucionDTO) {
        Resolucion resolucion = new Resolucion();
        if (!Objects.isNull(resolucionDTO)) {
            resolucion.setId(resolucionDTO.getId());
            resolucion.setNombre(resolucionDTO.getNombre());
            resolucion.setNumero(resolucionDTO.getNumero());
            resolucion.setExpediente(new Expediente());
            resolucion.setMontoOtorgado(resolucionDTO.getMontoOtorgado());
            resolucion.setFechaResolucion(resolucionDTO.getFechaResolucion());
            resolucion.setDescripcion(resolucionDTO.getDescripcion());
            resolucion.setFondosRendidos(resolucionDTO.isFondosRendidos());
        }
        return resolucion;
    }

//    public List<Resolucion> mapToListResolucion(List<ResolucionDTO> listaResolucionDTO) {
//        return ListMapper.mapListaToLista(listaResolucionDTO, this::mapToResolucion);
//    }

    public List<ResolucionDTO> mapToListResolucionDTO(List<Resolucion> listaResolucion) {
        return ListMapper.mapListaToLista(listaResolucion, this::mapToResolucionDTO);
    }
}
