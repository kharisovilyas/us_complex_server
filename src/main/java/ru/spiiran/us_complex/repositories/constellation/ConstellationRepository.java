package ru.spiiran.us_complex.repositories.constellation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;

@Repository
public interface ConstellationRepository extends JpaRepository<ConstellationEntity, Long> {
}
