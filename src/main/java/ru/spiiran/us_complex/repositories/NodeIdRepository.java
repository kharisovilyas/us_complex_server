package ru.spiiran.us_complex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;

import java.util.List;
import java.util.Optional;

public interface NodeIdRepository extends JpaRepository<IdNodeEntity, Long> {
    List<IdNodeEntity> findAllByOrderByNodeIdAsc();

    // Добавь этот метод в NodeIdRepository
    Optional<IdNodeEntity> findFirstByEarthPointEntityIsNotNullOrderByNodeIdDesc();
    @Query("SELECT MAX(i.nodeId) FROM IdNodeEntity i WHERE i.earthPointEntity IS NOT NULL")
    Long findMaxNodeIdWithEarthPoint();

    @Query("SELECT MAX(i.nodeId) FROM IdNodeEntity i")
    Long findMaxIdNode();

    @Query("SELECT MAX(i.nodeId) FROM IdNodeEntity i JOIN i.satellite s WHERE s.constellation.constellationId = :constellationId")
    Long findMaxConstellationIdNode(@Param("constellationId") Long constellationId);

    @Query("SELECT i FROM IdNodeEntity i JOIN i.satellite s WHERE s.constellation.constellationId = :constellationId")
    List<IdNodeEntity> findAllByConstellationId(@Param("constellationId") Long constellationId);
}
