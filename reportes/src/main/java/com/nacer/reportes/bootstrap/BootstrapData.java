package com.nacer.reportes.bootstrap;


import com.nacer.reportes.model.*;
import com.nacer.reportes.repository.efector.EfectorRepository;
import com.nacer.reportes.repository.expediente.ExpedienteRepository;
import com.nacer.reportes.repository.registro.RegistroRepository;
import com.nacer.reportes.repository.resolucion.ResolucionRepository;
import com.nacer.reportes.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EfectorRepository efectorRepository;
    private final RegistroRepository registroRepository;
    private final ExpedienteRepository expedienteRepository;
    private final ResolucionRepository resolucionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create and save User
        User user = new User("email@example.com", "password");
        user.setRoles(List.of(Rol.USER));
        userRepository.save(user);

        // Create and save Efector
        Efector efector = new Efector("CUIE123", Region.III);
        efectorRepository.save(efector);

        // Create and save Registros (5 records)
        for (int i = 1; i <= 5; i++) {
            Registro registro = new Registro(null, 100.0f * i, efector, TipoRegistro.DEBE);
            registroRepository.save(registro);
        }

        // Create and save Expediente
        Expediente expediente = new Expediente("EXP123456", efector, user, 5000.0f, LocalDate.now());
        expedienteRepository.save(expediente);

        // Create and save Resolucion
        Resolucion resolucion = new Resolucion("RES789", expediente, 2500.0f, LocalDate.now());
        resolucionRepository.save(resolucion);
    }
}
