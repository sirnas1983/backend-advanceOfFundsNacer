package com.nacer.reportes.repository.efector;

import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Region;
import com.nacer.reportes.model.RegionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface EfectorRepository extends JpaRepository<Efector, UUID> {

    Optional<Efector> findByCuie(String cuie);

    @Query("SELECT e FROM Efector e JOIN FETCH e.region r JOIN FETCH e.auditor a")
    List<Efector> findAllWithRegion();

    @Query("FROM Efector efector " +
            "WHERE (?1 is null OR efector.nombre LIKE ?1) "
    )
    List<Efector> findByNombre(String nombre);

    @Query("FROM Efector efector " +
            "WHERE (?1 is null OR efector.region = ?1) "
    )
    List<Efector> findByRegion(Region region);



}
