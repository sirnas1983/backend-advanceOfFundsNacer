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

    }
}
