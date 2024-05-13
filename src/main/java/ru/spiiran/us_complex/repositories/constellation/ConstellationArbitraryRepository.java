package ru.spiiran.us_complex.repositories.constellation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;

import java.util.List;


@Repository
public interface ConstellationArbitraryRepository extends JpaRepository<coArbitraryConstruction, Long> {
    List<coArbitraryConstruction> findByGeneralIdNodeEntity(generalIdNodeEntity idNodeEntity);
}
