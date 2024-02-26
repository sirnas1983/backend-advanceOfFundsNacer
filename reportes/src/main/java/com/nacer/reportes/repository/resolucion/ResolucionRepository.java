package com.nacer.reportes.repository.resolucion;

import com.nacer.reportes.model.Resolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResolucionRepository extends JpaRepository<Resolucion, UUID> {
}
