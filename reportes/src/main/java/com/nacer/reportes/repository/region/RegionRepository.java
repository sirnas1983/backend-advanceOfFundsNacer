package com.nacer.reportes.repository.region;

import com.nacer.reportes.model.Region;
import com.nacer.reportes.model.RegionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface RegionRepository extends JpaRepository<Region,UUID> {

    Region findByRegionEnum(RegionEnum regionEnum);

    @Query(value = "SELECT r.nombre, " +
            "COALESCE(SUM(CASE WHEN reg.tipo_registro = 'DEBE' THEN reg.monto ELSE 0 END), 0) AS sumaDebe, " +
            "COALESCE(SUM(CASE WHEN reg.tipo_registro = 'HABER' THEN reg.monto ELSE 0 END), 0) AS sumaHaber " +
            "FROM region r " +
            "LEFT JOIN efector e ON r.id = e.region " +
            "LEFT JOIN registro reg ON e.id = reg.efector " +
            "GROUP BY r.nombre", nativeQuery = true)
    List<Object[]> findAllSumaDebeSumaHaberByRegion();


}
