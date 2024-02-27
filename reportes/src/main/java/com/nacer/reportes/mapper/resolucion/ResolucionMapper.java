package com.nacer.reportes.mapper.resolucion;

import com.nacer.reportes.dto.ResolucionDTO;
import com.nacer.reportes.mapper.ListMapper;
import com.nacer.reportes.mapper.expediente.ExpedienteMapper;
import com.nacer.reportes.model.Resolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Component
public class ResolucionMapper {

    @Autowired
    private ExpedienteMapper expedienteMapper;

    public ResolucionDTO mapResolucionToResolucionDTO(Resolucion resolucion) {
        ResolucionDTO resolucionDTO = new ResolucionDTO();
        if (!Objects.isNull(resolucion)) {
            resolucionDTO.setId(resolucion.getId());
            resolucionDTO.setNombre(resolucion.getNombre());
            resolucionDTO.setNumero(resolucion.getNumero());
            resolucionDTO.setExpedienteDTO(expedienteMapper.mapExpedienteToExpedienteDTO(resolucion.getExpediente()));
            resolucionDTO.setMontoOtorgado(resolucion.getMontoOtorgado());
            resolucionDTO.setFechaResolucion(resolucion.getFechaResolucion());

        }
        return resolucionDTO;
    }

    public Resolucion mapResolucionDTOToResolucion(ResolucionDTO resolucionDTO) {
        Resolucion resolucion = new Resolucion();
        if (!Objects.isNull(resolucionDTO)) {
            resolucion.setId(resolucionDTO.getId());
            resolucion.setNombre(resolucionDTO.getNombre());
            resolucion.setNumero(resolucionDTO.getNumero());
            resolucion.setExpediente(expedienteMapper.mapExpedienteDTOToExpediente(resolucionDTO.getExpedienteDTO()));
            resolucion.setMontoOtorgado(resolucionDTO.getMontoOtorgado());
            resolucion.setFechaResolucion(resolucionDTO.getFechaResolucion());

        }
        return resolucion;
    }

    public List<Resolucion> mapListaResolucionDTOToListaResolucion(List<ResolucionDTO> listaResolucionDTO) {
        return ListMapper.mapListaToLista(listaResolucionDTO, this::mapResolucionDTOToResolucion);
    }

    public List<ResolucionDTO> mapListaResolucionToListaResolucionDTO(List<Resolucion> listaResolucion) {
        return ListMapper.mapListaToLista(listaResolucion, this::mapResolucionToResolucionDTO);
    }
}
