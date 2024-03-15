package ru.spiiran.us_complex.repositories.modelsat;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;

public interface ModelRepository extends JpaRepository<ModelSatEntity, Long> {
}
