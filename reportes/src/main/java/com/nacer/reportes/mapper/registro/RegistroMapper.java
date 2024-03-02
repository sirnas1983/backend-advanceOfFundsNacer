package com.nacer.reportes.mapper.registro;

import com.nacer.reportes.dto.RegistroDTO;
import com.nacer.reportes.mapper.ListMapper;
import com.nacer.reportes.mapper.efector.EfectorMapper;
import com.nacer.reportes.model.Registro;
import com.nacer.reportes.service.efector.EfectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class RegistroMapper {


    public RegistroDTO mapToRegistroDTO(Registro registro){
        RegistroDTO registroDTO = new RegistroDTO();
        if(!Objects.isNull(registro)){
            registroDTO.setTipoRegistro(registro.getTipoRegistro());
            registroDTO.setFecha(registro.getFecha());
            registroDTO.setId(registro.getId());
            registroDTO.setDetalle(registro.getDetalle());
            registroDTO.setMonto(registro.getMonto());
        }
        return registroDTO;
    }

    public Registro mapToRegistro(RegistroDTO registroDTO){
        Registro registro = new Registro();
        if(!Objects.isNull(registroDTO)){
            registro.setFecha(registroDTO.getFecha());
            registro.setId(registroDTO.getId());
            registro.setDetalle(registroDTO.getDetalle());
            registro.setTipoRegistro(registroDTO.getTipoRegistro());
            registro.setMonto(registroDTO.getMonto());
        }
        return registro;
    }

    public List<Registro> mapToListRegistros(List<RegistroDTO> listaRegistrosDTO) {
        return ListMapper.mapListaToLista(listaRegistrosDTO, this::mapToRegistro);
    }

    public List<RegistroDTO> mapToListRegistrosDTO(List<Registro> listaRegistros) {
        return ListMapper.mapListaToLista(listaRegistros, this::mapToRegistroDTO);
    }
}
