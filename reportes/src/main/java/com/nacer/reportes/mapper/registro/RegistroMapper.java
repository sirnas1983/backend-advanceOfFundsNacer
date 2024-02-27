package com.nacer.reportes.mapper.registro;

import com.nacer.reportes.dto.RegistroDTO;
import com.nacer.reportes.mapper.ListMapper;
import com.nacer.reportes.model.Registro;
import com.nacer.reportes.service.efector.EfectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Component
public class RegistroMapper {

    @Autowired
    EfectorService efectorService;


    public RegistroDTO mapRegistroToRegistroDTO(Registro registro){
        RegistroDTO registroDTO = new RegistroDTO();
        if(!Objects.isNull(registro)){
            registroDTO.setTipoRegistro(registro.getTipoRegistro());
            registroDTO.setId(registro.getId());
            registroDTO.setMonto(registro.getMonto());
            registroDTO.setEfectorCuie(registro.getEfector().getCuie());
        }
        return registroDTO;
    }

    public Registro mapRegistroDTOToRegistro(RegistroDTO registroDTO){
        Registro registro = new Registro();
        if(!Objects.isNull(registroDTO)){
            registro.setId(registroDTO.getId());
            registro.setTipoRegistro(registroDTO.getTipoRegistro());
            registro.setEfector(efectorService.getEfectorPorCuie(registroDTO.getEfectorCuie()).orElseThrow());
            registro.setMonto(registroDTO.getMonto());
        }
        return registro;
    }

    public List<Registro> mapListaRegistrosDtoToListaRegistros(List<RegistroDTO> listaRegistrosDTO) {
        return ListMapper.mapListaToLista(listaRegistrosDTO, this::mapRegistroDTOToRegistro);
    }

    public List<RegistroDTO> mapListaRegistrosToListaRegistrosDTO(List<Registro> listaRegistros) {
        return ListMapper.mapListaToLista(listaRegistros, this::mapRegistroToRegistroDTO);
    }

}
