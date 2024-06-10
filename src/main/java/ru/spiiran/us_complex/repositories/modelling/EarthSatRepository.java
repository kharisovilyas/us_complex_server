package ru.spiiran.us_complex.repositories.modelling;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.modelling.EarthSatEntity;

@Repository
public interface EarthSatRepository extends JpaRepository<EarthSatEntity, Long> {
}
