package ru.spiiran.us_complex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
