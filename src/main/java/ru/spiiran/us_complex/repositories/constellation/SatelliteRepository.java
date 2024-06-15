package ru.spiiran.us_complex.repositories.constellation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.constellation.SatelliteEntity;

import java.util.List;
@Repository
public interface SatelliteRepository extends JpaRepository<SatelliteEntity, Long> {
    @Query("SELECT c FROM SatelliteEntity c WHERE c.idNodeEntity.nodeId > :nodeId ORDER BY c.idNodeEntity.nodeId ASC")
    List<SatelliteEntity> findAllByNodeIdGreaterThan(@Param("nodeId") Long nodeId);

    // Метод для получения списка SatelliteEntity, отсортированного по nodeId
    @Query("SELECT s FROM SatelliteEntity s JOIN s.idNodeEntity n ORDER BY n.nodeId ASC")
    List<SatelliteEntity> findAllByOrderByNodeIdAsc();

    @Query("SELECT s FROM SatelliteEntity s WHERE s.constellation.constellationId <> :constellationId")
    List<SatelliteEntity> findAllNotInConstellation(@Param("constellationId") Long constellationId);
}
