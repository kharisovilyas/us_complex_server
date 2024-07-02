package ru.spiiran.us_complex.repositories.modelsat.power;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.modelsat.old.power.msModeEntity;

@Repository
public interface msModeRepository extends JpaRepository<msModeEntity, Long> {
}
