package ru.spiiran.us_complex.repositories.modelsat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.modelsat.power.msModeEntity;

@Repository
public interface msModeRepository extends JpaRepository<msModeEntity, Long> {
}
