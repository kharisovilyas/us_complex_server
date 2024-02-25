package ru.spiiran.us_complex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;

import java.util.List;

public interface IdNodeRepository extends JpaRepository<generalIdNodeEntity, Long> {
    @Query("SELECT MAX(g.idNode) FROM generalIdNodeEntity g")
    Long findMaxIdNode();

    List<generalIdNodeEntity> findAllByIdNodeGreaterThanEqual(Long idNode);
}
