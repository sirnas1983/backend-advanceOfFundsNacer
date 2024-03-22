package com.nacer.reportes.repository.registro;

import com.nacer.reportes.dto.others.ResumenRegionDTO;
import com.nacer.reportes.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, UUID> {
    @Query("FROM Registro registro " +
            "WHERE (?1 is null OR registro.efector.cuie LIKE ?1) "
    )
    List<Registro> findByCuie(String cuie);

    @Query("SELECT SUM(r.monto) FROM Registro r WHERE r.efector.cuie = :cuie AND r.tipoRegistro = 'DEBE'")
    Double getTotalDebeByCuie(@Param("cuie") String cuie);

    @Query("SELECT SUM(r.monto) FROM Registro r WHERE r.efector.cuie = :cuie AND r.tipoRegistro = 'HABER'")
    Double getTotalHaberByCuie(@Param("cuie") String cuie);

    @Query("SELECT r.efector.region.nombre, " +
            "COALESCE(SUM(CASE WHEN r.tipoRegistro = 'DEBE' THEN r.monto ELSE 0 END), 0), " +
            "COALESCE(SUM(CASE WHEN r.tipoRegistro = 'HABER' THEN r.monto ELSE 0 END), 0) " +
            "FROM Registro r " +
            "GROUP BY r.efector.region.nombre")
    List<Object[]> findAllSumaDebeSumaHaberByRegion();
}
