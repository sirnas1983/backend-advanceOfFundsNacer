package com.nacer.reportes.service.registro;

import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Registro;
import com.nacer.reportes.model.TipoRegistro;

import java.util.List;

public interface RegistroService {

    List<Registro> getRegistrosPorEfector(Efector efector);
    List<Registro> getRegistrosPorTipo(TipoRegistro tipo);
    void crearRegistro(Registro registro);

}
