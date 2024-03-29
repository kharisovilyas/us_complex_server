package ru.spiiran.us_complex.repositories.constellation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;

import java.util.List;

@Repository
public interface ConstellationRepository extends JpaRepository<ConstellationEntity, Long> {
    @Query("SELECT DISTINCT c FROM ConstellationEntity c LEFT JOIN FETCH c.coArbitraryConstructionList")
    List<ConstellationEntity> findAllWithArbitraryList();
}
