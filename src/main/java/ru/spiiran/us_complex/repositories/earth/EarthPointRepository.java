package ru.spiiran.us_complex.repositories.earth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;

@Repository
public interface EarthPointRepository extends JpaRepository<EarthPointEntity, Long> {
}
