package com.nacer.reportes.repository.registro;

import com.nacer.reportes.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, UUID> {
    @Query("FROM Registro registro " +
            "WHERE (?1 is null OR registro.efector.cuie LIKE ?1) "
    )
    List<Registro> findByCuie(String cuie);
}
