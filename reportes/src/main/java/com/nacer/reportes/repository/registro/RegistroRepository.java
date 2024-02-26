package com.nacer.reportes.repository.registro;

import com.nacer.reportes.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, UUID> {

}
