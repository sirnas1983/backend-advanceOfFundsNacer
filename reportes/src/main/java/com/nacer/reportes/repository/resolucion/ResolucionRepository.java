package com.nacer.reportes.repository.resolucion;

import com.nacer.reportes.model.Region;
import com.nacer.reportes.model.Resolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResolucionRepository extends JpaRepository<Resolucion, UUID> {
    Resolucion findByNumero(String numero);
    @Query("FROM Resolucion resolucion " +
            "WHERE (?1 is null OR resolucion.expediente.efector.cuie LIKE ?1) "
    )
    List<Resolucion> findByCuie(String cuie);

    @Query("FROM Resolucion resolucion " +
            "WHERE (?1 is null OR resolucion.expediente.numero LIKE ?1) "
    )
    Optional<Resolucion> findByNumeroExpediente(String numEx);

    @Query("FROM Resolucion resolucion " +
            "WHERE (?1 is null OR resolucion.expediente.efector.region = ?1) "
    )
    List<Resolucion> findByRegion(Region region);
}
