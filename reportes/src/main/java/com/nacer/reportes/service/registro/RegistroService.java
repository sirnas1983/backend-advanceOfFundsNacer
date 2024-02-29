package com.nacer.reportes.service.registro;

import com.nacer.reportes.dto.RegistroDTO;
import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Registro;
import com.nacer.reportes.model.TipoRegistro;

import java.util.List;

public interface RegistroService {


    void crearRegistro(RegistroDTO registroDTO);

    List<RegistroDTO> getTodosLosRegistros();

    void updateRegistro(RegistroDTO registroDTO);
}
