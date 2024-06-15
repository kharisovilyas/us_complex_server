package ru.spiiran.us_complex.repositories.earth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;

import java.util.List;

@Repository
public interface EarthPointRepository extends JpaRepository<EarthPointEntity, Long> {
    @Query("SELECT e FROM EarthPointEntity e WHERE e.idNodeEntity.nodeId > :nodeId ORDER BY e.idNodeEntity.nodeId ASC")
    List<EarthPointEntity> findAllByNodeIdGreaterThan(@Param("nodeId") Long nodeId);

    EarthPointEntity findByNameEarthPoint(String goalLabel);
}
