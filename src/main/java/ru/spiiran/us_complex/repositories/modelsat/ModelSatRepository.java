package ru.spiiran.us_complex.repositories.modelsat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;
@Repository
public interface ModelSatRepository extends JpaRepository<ModelSatEntity, Long> {
}
