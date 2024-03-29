package com.nacer.reportes.repository.expediente;

import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Expediente;
import com.nacer.reportes.model.RegionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpedienteRepository extends JpaRepository<Expediente, UUID> {

    Optional<Expediente> findByNumero(String numero);

    @Query("FROM Expediente e " +
            "WHERE (?1 is null OR e.efector = ?1) "
    )
    List<Expediente> findByEfector(Efector efector);
    @Query("FROM Expediente e " +
            "WHERE (?1 is null OR e.efector.cuie LIKE ?1) "
    )
    List<Expediente> findByCuie(String cuie);
    @Query("FROM Expediente e " +
            "WHERE (?1 is null OR e.efector.region = ?1)"
    )
    List<Expediente> findByRegion(RegionEnum regionEnum);
}